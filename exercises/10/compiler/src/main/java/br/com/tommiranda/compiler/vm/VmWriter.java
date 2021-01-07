package br.com.tommiranda.compiler.vm;

import br.com.tommiranda.compiler.ast.Node;
import br.com.tommiranda.compiler.ast.NodeType;
import br.com.tommiranda.compiler.errors.SyntaxError;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class VmWriter {

    public List<String> write(Node node) {
        List<Node> children = node.getChildren();

        String className = children.get(1).getValue();
        if (!SymbolTable.getClassName().equals(className)) {
            throw parseError(children.get(1), "The class name doesn't match the file name");
        }

        if (!SymbolTable.addClass(className)) {
            throw parseError(children.get(1), className + " class redefined");
        }

        List<String> vm_code = new ArrayList<>();

        for (Node child : children.subList(3, children.size())) {
            switch (child.getType()) {
                case CLASS_VAR_DEC -> classVarDec(child);
                case SUBROUTINE_DEC -> vm_code.addAll(subroutineDec(child));
            }
        }

        return vm_code;
    }

    private void classVarDec(Node node) {
        List<Node> children = node.getChildren();

        int i = 0;

        String kind = children.get(i++).getValue();
        String type = children.get(i++).getValue();

        do {
            String name = children.get(i++).getValue();

            if (!SymbolTable.addClassSymbol(name, type, SymbolKind.getKind(kind))) {
                throw parseError(node, name + " class variable redefined");
            }
        } while (children.get(i++).getValue().equals(","));
    }

    private List<String> subroutineDec(Node node) {
        List<Node> children = node.getChildren();

        String kind = children.get(0).getValue();
        SubroutineKind subroutineKind = SubroutineKind.getKind(kind);
        String type = children.get(1).getValue();
        String subroutineName = children.get(2).getValue();

        if (!SymbolTable.addSubroutine(subroutineName)) {
            throw parseError(children.get(2), "Subroutine " + subroutineName + " redefined");
        }

        SymbolTable.startSubroutine(subroutineName, type, subroutineKind);

        parameterList(children.get(4));

        List<String> vm_body = subroutineBody(children.get(6));

        var vm_code = new ArrayList<String>();

        vm_code.add("function " + SymbolTable.getClassName() + "." + subroutineName + " " + SymbolTable.countSymbols(SymbolKind.LOCAL));

        if (subroutineKind.equals(SubroutineKind.CONSTRUCTOR)) {
            vm_code.add("push constant " + SymbolTable.countSymbols(SymbolKind.FIELD));
            vm_code.add("call Memory.alloc 1");
            vm_code.add("pop pointer 0");
        } else if (subroutineKind.equals(SubroutineKind.METHOD)) {
            vm_code.add("push argument 0");
            vm_code.add("pop pointer 0");
        }

        vm_code.addAll(vm_body);

        SymbolTable.endSubroutine(subroutineName);

        return vm_code;
    }

    private void parameterList(Node node) {
        int i = 0;

        List<Node> children = node.getChildren();

        while (children.size() > i) {
            String argType = children.get(i++).getValue();
            String argName = children.get(i++).getValue();

            if (!SymbolTable.addSubroutineSymbol(argName, argType, SymbolKind.ARGUMENT)) {
                throw parseError(children.get(i - 1), "argument " + argName + " redefined");
            }

            i++;
        }
        ;
    }

    private List<String> subroutineBody(Node node) {
        List<Node> children = node.getChildren();

        int i = 1;

        while (children.get(i).getType().equals(NodeType.VAR_DEC)) {
            varDec(children.get(i++));
        }

        return statements(children.get(i));
    }

    private void varDec(Node node) {
        List<Node> children = node.getChildren();

        int i = 1;

        String localType = children.get(i++).getValue();

        do {
            String localName = children.get(i++).getValue();

            if (!SymbolTable.addSubroutineSymbol(localName, localType, SymbolKind.LOCAL)) {
                throw parseError(node, "Local variable " + localName + " redefined");
            }
        } while (children.get(i++).getValue().equals(","));
    }

    private List<String> statements(Node node) {
        List<Node> children = node.getChildren();

        boolean lastStatementReturn = false;

        List<String> vm_code = new ArrayList<>();

        for (Node child : children) {
            switch (child.getType()) {
                case DO_STATEMENT -> vm_code.addAll(doStatement(child));
                case IF_STATEMENT -> vm_code.addAll(ifStatement(child));
                case LET_STATEMENT -> vm_code.addAll(letStatement(child));
                case RETURN_STATEMENT -> vm_code.addAll(returnStatement(child));
                case WHILE_STATEMENT -> vm_code.addAll(whileStatement(child));
            }
        }

        return vm_code;
    }

    private List<String> doStatement(Node node) {
        return Collections.emptyList();
    }

    private List<String> ifStatement(Node node) {
        List<Node> children = node.getChildren();

        return Collections.emptyList();
    }

    private List<String> letStatement(Node node) {
        List<Node> children = node.getChildren();

        List<String> vm_code = new ArrayList<>();

        String variable = children.get(1).getValue();
        SymbolAttribute symbolAttribute = SymbolTable.getSymbol(variable);
        if (symbolAttribute == null) {
            throw parseError(children.get(1), variable + " undefined");
        }

        if (children.get(2).getValue().equals("[")) {
            vm_code.add("push " + symbolAttribute.getKind().getName() + " " + symbolAttribute.getIndex());
            vm_code.addAll(expression(children.get(3)));
            vm_code.add("add");
            vm_code.addAll(expression(children.get(6)));
            vm_code.add("pop temp 0");
            vm_code.add("pop pointer 1");
            vm_code.add("push temp 0");
            vm_code.add("pop that 0");
        } else {
            vm_code.addAll(expression(children.get(3)));

            vm_code.add("pop " + symbolAttribute.getKind().getName() + " " + symbolAttribute.getIndex());
        }

        return vm_code;
    }

    private List<String> returnStatement(Node node) {
        List<String> vm_code = new ArrayList<>();

        if (SymbolTable.getActualSubroutineKind().equals(SubroutineKind.CONSTRUCTOR)) {
            vm_code.add("push pointer 0");
        } else if (SymbolTable.getActualSubroutineType().equals("void")) {
            vm_code.add("push constant 0");
        }

        vm_code.add("return");

        return vm_code;
    }

    private List<String> whileStatement(Node node) {
        return Collections.emptyList();
    }

    private List<String> expression(Node node) {
        List<Node> children = node.getChildren();

        List<String> vm_code = new ArrayList<>();

        for (Node child : children) {
            if(child.getType().equals(NodeType.TERM)) {
                vm_code.addAll(term(children.get(0)));
            } else if (child.getType().equals(NodeType.SYMBOL)) {
                vm_code.add(SymbolToVM.convert(child.getValue()));
            }
        }

        return vm_code;
    }

    private List<String> term(Node node) {
        List<Node> children = node.getChildren();

        List<String> vm_code = new ArrayList<>();

        Node child = children.get(0);
        if (children.size() == 1) {
            if (child.getType().equals(NodeType.INTEGER_CONSTANT)) {
                vm_code.add("push constant " + child.getValue());
            } else if (child.getType().equals(NodeType.STRING_CONSTANT)) {
                String string = child.getValue().substring(1, child.getValue().length() - 1);

                vm_code.add("push constant " + string.length());
                vm_code.add("call String.new 1");

                for (int i = 0; i < string.length(); i++) {
                    short c = (short) string.charAt(i);

                    vm_code.add("push constant " + c);
                    vm_code.add("call String.appendChar 2");
                }
            } else if (child.getType().equals(NodeType.IDENTIFIER)) {
                String variable = child.getValue();
                SymbolAttribute symbolAttribute = SymbolTable.getSymbol(variable);
                if (symbolAttribute == null) {
                    throw parseError(children.get(1), variable + " undefined");
                }

                vm_code.add("push " + symbolAttribute.getKind().getName() + " " + symbolAttribute.getIndex());
            } else if(child.getValue().equals("false")) {
                vm_code.add("push constant 0");
            } else if(child.getValue().equals("true")) {
                vm_code.add("push constant 0");
                vm_code.add("not");
            }
        } else {
            if(child.getValue().equals("-")) {
                term(children.get(1));
                vm_code.add("neg");
            } else if(child.getValue().equals("~")) {
                term(children.get(1));
                vm_code.add("not");
            } else if(child.getValue().equals("(")) {
                expression(children.get(1));
            } else if(child.getType().equals(NodeType.IDENTIFIER)) {
                String variable = child.getValue();
                SymbolAttribute symbolAttribute = SymbolTable.getSymbol(variable);
                if (symbolAttribute == null) {
                    throw parseError(children.get(1), variable + " undefined");
                }

                
            }
        }

        return vm_code;
    }

    SyntaxError parseError(Node node, String message) {
        return new SyntaxError("(line " + node.getLineNumber() + "): " + message);
    }
}