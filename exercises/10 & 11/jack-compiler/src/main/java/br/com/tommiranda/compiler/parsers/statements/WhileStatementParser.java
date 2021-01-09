package br.com.tommiranda.compiler.parsers.statements;

import br.com.tommiranda.compiler.ast.Node;
import br.com.tommiranda.compiler.ast.NodeType;
import br.com.tommiranda.compiler.parsers.Parser;
import br.com.tommiranda.compiler.parsers.expressions.ExpressionParser;
import br.com.tommiranda.compiler.tokenizer.Token;

import java.util.ArrayList;
import java.util.List;

public class WhileStatementParser implements Parser {

    @Override
    public Node parse(List<Token> tokens) {
        var children = new ArrayList<Node>();

        Token token = tokens.remove(0);
        if (!token.getValue().equals("while")) {
            throw parseError(token, "Expected while");
        }
        children.add(new Node(token));

        token = tokens.remove(0);
        if (!token.getValue().equals("(")) {
            throw parseError(token, "Expected (");
        }
        children.add(new Node(token));

        children.add(new ExpressionParser().parse(tokens));

        token = tokens.remove(0);
        if (!token.getValue().equals(")")) {
            throw parseError(token, "Expected )");
        }
        children.add(new Node(token));

        token = tokens.remove(0);
        if (!token.getValue().equals("{")) {
            throw parseError(token, "Expected {");
        }
        children.add(new Node(token));

        children.add(new StatementsParser().parse(tokens));

        token = tokens.remove(0);
        if (!token.getValue().equals("}")) {
            throw parseError(token, "Expected }");
        }
        children.add(new Node(token));

        return new Node(NodeType.WHILE_STATEMENT, children);
    }
}
