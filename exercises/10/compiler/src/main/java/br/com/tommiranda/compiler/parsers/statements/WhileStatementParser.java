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
        List<Node> children = new ArrayList<>();

        Token token = tokens.remove(0);
        if (!token.getValue().equals("while")) {
            throw parseError(token, "Expected while");
        }
        children.add(new Node(NodeType.KEYWORD, token.getValue()));

        token = tokens.remove(0);
        if (!token.getValue().equals("(")) {
            throw parseError(token, "Expected (");
        }
        children.add(new Node(NodeType.SYMBOL, token.getValue()));

        children.add(new ExpressionParser().parse(tokens));

        token = tokens.remove(0);
        if (!token.getValue().equals(")")) {
            throw parseError(token, "Expected )");
        }
        children.add(new Node(NodeType.SYMBOL, token.getValue()));

        token = tokens.remove(0);
        if (!token.getValue().equals("{")) {
            throw parseError(token, "Expected {");
        }
        children.add(new Node(NodeType.SYMBOL, token.getValue()));

        children.add(new StatementsParser().parse(tokens));

        token = tokens.remove(0);
        if (!token.getValue().equals("}")) {
            throw parseError(token, "Expected }");
        }
        children.add(new Node(NodeType.SYMBOL, token.getValue()));

        return new Node(NodeType.WHILE_STATEMENT, children);
    }
}
