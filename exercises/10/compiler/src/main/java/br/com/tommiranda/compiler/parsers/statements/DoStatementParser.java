package br.com.tommiranda.compiler.parsers.statements;

import br.com.tommiranda.compiler.ast.Node;
import br.com.tommiranda.compiler.ast.NodeType;
import br.com.tommiranda.compiler.parsers.Parser;
import br.com.tommiranda.compiler.parsers.expressions.SubroutineCallParser;
import br.com.tommiranda.compiler.tokenizer.Token;

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

        children.add(new SubroutineCallParser().parse(tokens));

        token = tokens.remove(0);
        if (!token.getValue().equals(";")) {
            throw parseError(token, "Expected ;");
        }
        children.add(new Node(NodeType.SYMBOL, token.getValue()));

        return new Node(NodeType.DO_STATEMENT, children);
    }
}
