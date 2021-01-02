package br.com.tommiranda.compiler.parsers.structure;

import br.com.tommiranda.compiler.ast.Node;
import br.com.tommiranda.compiler.ast.NodeType;
import br.com.tommiranda.compiler.parsers.Parser;
import br.com.tommiranda.compiler.tokenizer.Token;
import br.com.tommiranda.compiler.tokenizer.TokenType;

import java.util.ArrayList;
import java.util.List;

public class ClassParser implements Parser {

    @Override
    public Node parse(List<Token> tokens) {
        var children = new ArrayList<Node>();

        Token token = tokens.remove(0);
        if (!token.getValue().equals("class")) {
            throw parseError(token, "Expected 'class'");
        }
        children.add(new Node(NodeType.KEYWORD, token.getValue()));

        token = tokens.remove(0);
        if (!token.getType().equals(TokenType.IDENTIFIER)) {
            throw parseError(token, "Expected a class name");
        }
        children.add(new Node(NodeType.IDENTIFIER, token.getValue()));

        token = tokens.remove(0);
        if (!token.getValue().equals("{")) {
            throw parseError(token, "Expected {");
        }
        children.add(new Node(NodeType.SYMBOL, token.getValue()));

        token = tokens.get(0);
        while (token.getValue().equals("field") || token.getValue().equals("static")) {
            children.add(new ClassVarDecParser().parse(tokens));
            token = tokens.get(0);
        }

        token = tokens.get(0);
        while (token.getValue().equals("constructor") || token.getValue().equals("function") || token.getValue().equals("method")) {
            children.add(new SubroutineDecParser().parse(tokens));
            token = tokens.get(0);
        }

        token = tokens.remove(0);
        if (!token.getValue().equals("}")) {
            throw parseError(token, "Expected }");
        }
        children.add(new Node(NodeType.SYMBOL, token.getValue()));

        return new Node(NodeType.CLASS, children);
    }
}
