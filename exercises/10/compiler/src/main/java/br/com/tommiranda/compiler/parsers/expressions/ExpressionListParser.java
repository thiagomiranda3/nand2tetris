package br.com.tommiranda.compiler.parsers.expressions;

import br.com.tommiranda.compiler.ast.Node;
import br.com.tommiranda.compiler.ast.NodeType;
import br.com.tommiranda.compiler.parsers.Parser;
import br.com.tommiranda.compiler.tokenizer.Token;

import java.util.ArrayList;
import java.util.List;

public class ExpressionListParser implements Parser {

    @Override
    public Node parse(List<Token> tokens) {
        var children = new ArrayList<Node>();

        children.add(new ExpressionParser().parse(tokens));

        Token token = tokens.get(0);
        while (token.getValue().equals(",")) {
            token = tokens.remove(0);
            children.add(new Node(token));

            children.add(new ExpressionParser().parse(tokens));

            token = tokens.get(0);
        }

        return new Node(NodeType.EXPRESSION_LIST, children);
    }
}
