package br.com.tommiranda.compiler.parsers.expressions;

import br.com.tommiranda.compiler.ast.Node;
import br.com.tommiranda.compiler.ast.NodeType;
import br.com.tommiranda.compiler.parsers.Elements;
import br.com.tommiranda.compiler.parsers.Parser;
import br.com.tommiranda.compiler.tokenizer.Token;

import java.util.ArrayList;
import java.util.List;

public class ExpressionParser implements Parser {

    @Override
    public Node parse(List<Token> tokens) {
        var children = new ArrayList<Node>();

        children.add(new TermParser().parse(tokens));

        Token token = tokens.get(0);
        while (Elements.isOp(token.getValue())) {
            token = tokens.remove(0);
            children.add(new Node(token));

            children.add(new TermParser().parse(tokens));

            token = tokens.get(0);
        }

        return new Node(NodeType.EXPRESSION, children);
    }
}
