package br.com.tommiranda.compiler.parsers.expressions;

import br.com.tommiranda.compiler.ast.Node;
import br.com.tommiranda.compiler.ast.NodeType;
import br.com.tommiranda.compiler.parsers.Elements;
import br.com.tommiranda.compiler.parsers.Parser;
import br.com.tommiranda.compiler.tokenizer.Token;
import br.com.tommiranda.compiler.tokenizer.TokenType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class TermParser implements Parser {

    @Override
    public Node parse(List<Token> tokens) {
        var children = new ArrayList<Node>();

        Token token = tokens.remove(0);
        if (token.getType().equals(TokenType.INTEGER) || token.getType().equals(TokenType.STRING) || Elements.isConstant(token.getValue())) {
            children.add(new Node(token));
        } else if (token.getType().equals(TokenType.IDENTIFIER)) {
            children.add(new Node(token));

            token = tokens.get(0);
            if (token.getValue().equals("[")) {
                token = tokens.remove(0);
                children.add(new Node(token));

                children.add(new ExpressionParser().parse(tokens));

                token = tokens.remove(0);
                if (!token.getValue().equals("]")) {
                    throw parseError(token, "Expected ]");
                }
                children.add(new Node(token));
            } else if (token.getValue().equals("(")) {
                token = tokens.remove(0);
                children.add(new Node(token));

                children.add(new ExpressionListParser().parse(tokens));

                token = tokens.remove(0);
                if (!token.getValue().equals(")")) {
                    throw parseError(token, "Expected )");
                }
                children.add(new Node(token));
            } else if (token.getValue().equals(".")) {
                token = tokens.remove(0);
                children.add(new Node(token));

                token = tokens.remove(0);
                if (!token.getType().equals(TokenType.IDENTIFIER)) {
                    throw parseError(token, "Expected subroutine name");
                }
                children.add(new Node(token));

                token = tokens.remove(0);
                if (!token.getValue().equals("(")) {
                    throw parseError(token, "Expected (");
                }
                children.add(new Node(token));

                token = tokens.get(0);
                if (!token.getValue().equals(")")) {
                    children.add(new ExpressionListParser().parse(tokens));
                } else {
                    children.add(new Node(NodeType.EXPRESSION_LIST, Collections.emptyList()));
                }

                token = tokens.remove(0);
                if (!token.getValue().equals(")")) {
                    throw parseError(token, "Expected )");
                }
                children.add(new Node(token));
            }
        } else if (token.getValue().equals("(")) {
            children.add(new Node(token));

            children.add(new ExpressionParser().parse(tokens));

            token = tokens.remove(0);
            if (!token.getValue().equals(")")) {
                throw parseError(token, "Expected )");
            }
            children.add(new Node(token));
        } else if (Elements.isUnaryOp(token.getValue())) {
            children.add(new Node(token));

            children.add(new TermParser().parse(tokens));
        } else if (token.getType().equals(TokenType.KEYWORD)) {
            throw parseError(token, "Illegal keyword in term");
        } else {
            throw parseError(token, "Invalid Expression");
        }

        return new Node(NodeType.TERM, children);
    }
}
