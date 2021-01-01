package br.com.tommiranda.compiler.parsers.statements;

import br.com.tommiranda.compiler.ast.Node;
import br.com.tommiranda.compiler.ast.NodeType;
import br.com.tommiranda.compiler.parsers.Parser;
import br.com.tommiranda.compiler.parsers.expressions.ExpressionListParser;
import br.com.tommiranda.compiler.parsers.expressions.ExpressionParser;
import br.com.tommiranda.compiler.tokenizer.Token;
import br.com.tommiranda.compiler.tokenizer.TokenType;

import java.util.ArrayList;
import java.util.List;

public class DoStatementParser implements Parser {

    @Override
    public Node parse(List<Token> tokens) {
        List<Node> children = new ArrayList<>();

        Token token = tokens.remove(0);
        if (!token.getValue().equals("do")) {
            throw parseError(token, "Expected do");
        }
        children.add(new Node(NodeType.KEYWORD, token.getValue()));

        token = tokens.remove(0);
        if (!token.getType().equals(TokenType.IDENTIFIER)) {
            throw parseError(token, "Expected class name, subroutine name, field, parameter or local or static variable name");
        }
        children.add(new Node(NodeType.IDENTIFIER, token.getValue()));

        token = tokens.get(0);
        if (token.getValue().equals(".")) {
            token = tokens.remove(0);
            children.add(new Node(NodeType.SYMBOL, token.getValue()));

            token = tokens.remove(0);
            if (!token.getType().equals(TokenType.IDENTIFIER)) {
                throw parseError(token, "Expected subroutine name");
            }
            children.add(new Node(NodeType.IDENTIFIER, token.getValue()));
        }

        token = tokens.remove(0);
        if (!token.getValue().equals("(")) {
            throw parseError(token, "Expected (");
        }
        children.add(new Node(NodeType.SYMBOL, token.getValue()));

        children.add(new ExpressionListParser().parse(tokens));

        token = tokens.remove(0);
        if (!token.getValue().equals(")")) {
            throw parseError(token, "Expected )");
        }
        children.add(new Node(NodeType.SYMBOL, token.getValue()));

        token = tokens.remove(0);
        if (!token.getValue().equals(";")) {
            throw parseError(token, "Expected ;");
        }
        children.add(new Node(NodeType.SYMBOL, token.getValue()));

        return new Node(NodeType.DO_STATEMENT, children);
    }
}
