package br.com.tommiranda.compiler.parsers.statements;

import br.com.tommiranda.compiler.ast.Node;
import br.com.tommiranda.compiler.ast.NodeType;
import br.com.tommiranda.compiler.parsers.Parser;
import br.com.tommiranda.compiler.parsers.expressions.ExpressionParser;
import br.com.tommiranda.compiler.tokenizer.Token;

import java.util.ArrayList;
import java.util.List;

public class ReturnStatementParser implements Parser {

    @Override
    public Node parse(List<Token> tokens) {
        var children = new ArrayList<Node>();

        Token token = tokens.remove(0);
        if (!token.getValue().equals("return")) {
            throw parseError(token, "Expected return");
        }
        children.add(new Node(token));

        token = tokens.get(0);
        if (token.getValue().equals(";")) {
            token = tokens.remove(0);
            children.add(new Node(token));

            return new Node(NodeType.RETURN_STATEMENT, children);
        }

        children.add(new ExpressionParser().parse(tokens));

        token = tokens.remove(0);
        if (!token.getValue().equals(";")) {
            throw parseError(token, "Expected ;");
        }
        children.add(new Node(token));

        return new Node(NodeType.RETURN_STATEMENT, children);
    }
}
