package br.com.tommiranda.compiler.parsers.expressions;

import br.com.tommiranda.compiler.ast.Node;
import br.com.tommiranda.compiler.ast.NodeType;
import br.com.tommiranda.compiler.parsers.Elements;
import br.com.tommiranda.compiler.parsers.Parser;
import br.com.tommiranda.compiler.tokenizer.Token;
import br.com.tommiranda.compiler.tokenizer.TokenType;

import java.util.ArrayList;
import java.util.List;

public class TermParser implements Parser {

    @Override
    public Node parse(List<Token> tokens) {
        List<Node> children = new ArrayList<>();

        Token token = tokens.remove(0);
        if (token.getType().equals(TokenType.INTEGER) || token.getType().equals(TokenType.STRING) || Elements.isConstant(token.getValue())) {
            children.add(new Node(NodeType.fromToken(token.getType()), token.getValue()));
        } else if (token.getType().equals(TokenType.IDENTIFIER)) {
            children.add(new Node(NodeType.IDENTIFIER, token.getValue()));

            token = tokens.get(0);
            if (token.getValue().equals("[")) {
                token = tokens.remove(0);
                children.add(new Node(NodeType.SYMBOL, token.getValue()));

                children.add(new ExpressionParser().parse(tokens));

                token = tokens.remove(0);
                if (!token.getValue().equals("]")) {
                    throw parseError(token, "Expected ]");
                }
                children.add(new Node(NodeType.SYMBOL, token.getValue()));
            }
        } else if (token.getValue().equals("(")) {
            children.add(new Node(NodeType.SYMBOL, token.getValue()));

            children.add(new ExpressionParser().parse(tokens));

            token = tokens.remove(0);
            if (!token.getValue().equals(")")) {
                throw parseError(token, "Expected )");
            }
            children.add(new Node(NodeType.SYMBOL, token.getValue()));
        } else if (Elements.isUnaryOp(token.getValue())) {
            children.add(new Node(NodeType.SYMBOL, token.getValue()));

            children.add(new TermParser().parse(tokens));
        } else {
            children.add(new SubroutineCallParser().parse(tokens));
        }

        return new Node(NodeType.TERM, children);
    }
}
