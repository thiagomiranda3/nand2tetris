package br.com.tommiranda.compiler.parsers.structure;

import br.com.tommiranda.compiler.ast.Node;
import br.com.tommiranda.compiler.ast.NodeType;
import br.com.tommiranda.compiler.parsers.Elements;
import br.com.tommiranda.compiler.parsers.Parser;
import br.com.tommiranda.compiler.tokenizer.Token;
import br.com.tommiranda.compiler.tokenizer.TokenType;

import java.util.ArrayList;
import java.util.List;

public class ClassVarDecParser implements Parser {

    @Override
    public Node parse(List<Token> tokens) {
        List<Node> children = new ArrayList<>();

        Token token = tokens.remove(0);
        if (!token.getValue().equals("field") && !token.getValue().equals("static")) {
            throw parseError(token, "Expected field or static");
        }

        children.add(new Node(NodeType.KEYWORD, token.getValue()));

        token = tokens.remove(0);
        if (!Elements.isType(token.getValue()) && !token.getType().equals(TokenType.IDENTIFIER)) {
            throw parseError(token, "Expected primitive type or class name");
        }

        children.add(new Node(NodeType.fromToken(token.getType()), token.getValue()));

        token = tokens.remove(0);
        if (!token.getType().equals(TokenType.IDENTIFIER)) {
            throw parseError(token, "Expected a type followed by comma-separated variable names");
        }

        children.add(new Node(NodeType.IDENTIFIER, token.getValue()));

        token = tokens.remove(0);
        while (token.getValue().equals(",")) {
            children.add(new Node(NodeType.SYMBOL, token.getValue()));

            token = tokens.remove(0);
            if (!token.getType().equals(TokenType.IDENTIFIER)) {
                throw parseError(token, "Expected a type followed by comma-separated variable names");
            }

            children.add(new Node(NodeType.IDENTIFIER, token.getValue()));

            token = tokens.remove(0);
        }

        if(!token.getValue().equals(";")) {
            throw parseError(token, "Expected , or ;");
        }

        return new Node(NodeType.CLASS_VAR_DEC, children);
    }
}
