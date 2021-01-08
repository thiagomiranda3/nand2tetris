package br.com.tommiranda.compiler.vm;

import br.com.tommiranda.compiler.ast.Node;
import br.com.tommiranda.compiler.ast.NodeType;
import br.com.tommiranda.compiler.errors.SyntaxError;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class VmWriter {

    private int ifCounter = 0;
    private int whileCounter = 0;

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
        this.ifCounter = 0;
        this.whileCounter = 0;

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

        String lastCode = vm_code.get(vm_code.size() - 1);
        if (!lastCode.equals("return")) {
            throw parseError(children.get(2), "Subroutine " + subroutineName + " don't end with a return statament");
        }

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

        boolean returnAlreadyUsed = false;

        List<String> vm_code = new ArrayList<>();

        for (Node child : children) {
            if (returnAlreadyUsed) {
                throw parseError(child, "Unreachable code");
            }

            switch (child.getType()) {
                case DO_STATEMENT -> vm_code.addAll(doStatement(child));
                case IF_STATEMENT -> vm_code.addAll(ifStatement(child));
                case LET_STATEMENT -> vm_code.addAll(letStatement(child));
                case RETURN_STATEMENT -> {
                    vm_code.addAll(returnStatement(child));
                    returnAlreadyUsed = true;
                }
                case WHILE_STATEMENT -> vm_code.addAll(whileStatement(child));
            }
        }

        return vm_code;
    }

    private List<String> doStatement(Node node) {
        List<Node> children = node.getChildren();

        List<String> vm_code = new ArrayList<>();

        Node child = children.get(0);
        if (child.getType().equals(NodeType.IDENTIFIER)) {
            String variable = child.getValue();
            SymbolAttribute symbolAttribute = SymbolTable.getSymbol(variable);

            if (children.get(1).getValue().equals(".")) {
                String subroutineName = children.get(2).getValue();

                long numParameters = countParameters(children, 4);
                vm_code.addAll(expressionList(children.get(4)));

                if (symbolAttribute != null) {
                    vm_code.add("call " + symbolAttribute.getType() + "." + " " + numParameters);
                } else {
                    vm_code.add("call " + variable + "." + subroutineName + " " + numParameters);
                }
            } else if (children.get(1).getValue().equals("(")) {
                String subroutineName = child.getValue();

                if (!SymbolTable.containsSubroutine(subroutineName)) {
                    throw parseError(child, "Method " + SymbolTable.getClassName() + "." + subroutineName + " doesn't exist");
                }

                vm_code.addAll(expressionList(children.get(2)));
                long numParameters = countParameters(children, 2);

                vm_code.add("call " + SymbolTable.getClassName() + "." + subroutineName + " " + numParameters);
            }
        }

        vm_code.add("pop temp 0");

        return vm_code;
    }

    private List<String> ifStatement(Node node) {
        int counter = ifCounter++;

        List<Node> children = node.getChildren();

        List<String> vm_code = new ArrayList<>();

        vm_code.addAll(expression(children.get(2)));

        if (!vm_code.get(vm_code.size() - 1).equals("eq")) {
            vm_code.add("eq");
        }

        vm_code.add("if-goto IF_TRUE" + counter);
        vm_code.add("goto IF_FALSE" + counter);
        vm_code.add("label IF_TRUE" + counter);

        vm_code.addAll(statements(children.get(5)));

        if (children.size() > 7 && children.get(7).getValue().equals("else")) {
            vm_code.add("goto IF_END" + counter);
            vm_code.add("label IF_FALSE" + counter);

            vm_code.addAll(statements(children.get(9)));

            vm_code.add("label IF_END" + counter);
        } else {
            vm_code.add("label IF_FALSE" + counter);
        }

        return vm_code;
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

        Node expression = node.getChildren().get(1);

        if (SymbolTable.getActualSubroutineKind().equals(SubroutineKind.CONSTRUCTOR)) {
            if (expression.getChildren().isEmpty()) {
                throw parseError(expression, "A constructor must return 'this'");
            }

            Node term = expression.getChildren().get(0);

            if (term.getType().equals(NodeType.TERM)) {
                String returned = term.getChildren().get(0).getValue();

                if (!returned.equals("this")) {
                    throw parseError(term, "A constructor must return 'this'");
                }
            } else {
                throw parseError(term, "A constructor must return 'this'");
            }

            vm_code.add("push pointer 0");
        } else if (SymbolTable.getActualSubroutineType().equals("void")) {
            if (expression.getType().equals(NodeType.EXPRESSION)) {
                throw parseError(expression, "A void function must not return a value");
            }

            vm_code.add("push constant 0");
        }

        vm_code.addAll(expression(expression));
        vm_code.add("return");

        return vm_code;
    }

    private List<String> whileStatement(Node node) {
        int counter = this.whileCounter++;

        List<Node> children = node.getChildren();

        List<String> vm_code = new ArrayList<>();

        vm_code.add("label WHILE_EXP" + counter);

        vm_code.addAll(expression(children.get(2)));

        if (!vm_code.get(vm_code.size() - 1).equals("eq")) {
            vm_code.add("eq");
        }

        vm_code.add("not");
        vm_code.add("if-goto WHILE_END" + counter);

        vm_code.addAll(statements(children.get(5)));

        vm_code.add("goto WHILE_EXP" + counter);
        vm_code.add("label WHILE_END" + counter);

        return vm_code;
    }

    private List<String> expression(Node node) {
        List<Node> children = node.getChildren();

        String op = "";
        List<String> vm_code = new ArrayList<>();

        for (Node child : children) {
            if (child.getType().equals(NodeType.TERM)) {
                vm_code.addAll(term(child));

                if (!op.isBlank()) {
                    vm_code.add(op);
                    op = "";
                }
            } else if (child.getType().equals(NodeType.SYMBOL)) {
                op = SymbolToVM.convert(child.getValue());
            }
        }

        return vm_code;
    }

    private List<String> expressionList(Node node) {
        List<Node> children = node.getChildren();

        List<String> vm_code = new ArrayList<>();

        for (Node child : children) {
            vm_code.addAll(expression(child));
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
            } else if (child.getValue().equals("false")) {
                vm_code.add("push constant 0");
            } else if (child.getValue().equals("true")) {
                vm_code.add("push constant 0");
                vm_code.add("not");
            }
        } else {
            if (child.getValue().equals("-")) {
                vm_code.addAll(term(children.get(1)));
                vm_code.add("neg");
            } else if (child.getValue().equals("~")) {
                vm_code.addAll(term(children.get(1)));
                vm_code.add("not");
            } else if (child.getValue().equals("(")) {
                vm_code.addAll(expression(children.get(1)));
            } else if (child.getType().equals(NodeType.IDENTIFIER)) {
                String variable = child.getValue();
                SymbolAttribute symbolAttribute = SymbolTable.getSymbol(variable);

                if (children.get(1).getValue().equals(".")) {
                    String subroutineName = children.get(2).getValue();

                    long numParameters = countParameters(children, 4);
                    vm_code.addAll(expressionList(children.get(4)));

                    if (symbolAttribute != null) {
                        vm_code.add("call " + symbolAttribute.getType() + "." + " " + numParameters);
                    } else {
                        vm_code.add("call " + variable + "." + subroutineName + " " + numParameters);
                    }
                } else if (children.get(1).getValue().equals("(")) {
                    String subroutineName = child.getValue();

                    if (!SymbolTable.containsSubroutine(subroutineName)) {
                        throw parseError(child, "Method " + SymbolTable.getClassName() + "." + subroutineName + " doesn't exist");
                    }

                    vm_code.addAll(expressionList(children.get(2)));
                    long numParameters = countParameters(children, 2);

                    vm_code.add("call " + SymbolTable.getClassName() + "." + subroutineName + " " + numParameters);
                }
            }
        }

        return vm_code;
    }

    private long countParameters(List<Node> children, int index) {
        return children.get(index)
                       .getChildren()
                       .stream()
                       .filter(e -> e.getType().equals(NodeType.EXPRESSION))
                       .count();
    }

    SyntaxError parseError(Node node, String message) {
        return new SyntaxError("(line " + node.getLineNumber() + "): " + message);
    }
}