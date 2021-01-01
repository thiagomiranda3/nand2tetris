package br.com.tommiranda.compiler.parsers.structure;

import br.com.tommiranda.compiler.ast.Node;
import br.com.tommiranda.compiler.ast.NodeType;
import br.com.tommiranda.compiler.parsers.Parser;
import br.com.tommiranda.compiler.parsers.statements.StatementsParser;
import br.com.tommiranda.compiler.tokenizer.Token;

import java.util.ArrayList;
import java.util.List;

public class SubroutineBodyParser implements Parser {

    @Override
    public Node parse(List<Token> tokens) {
        List<Node> children = new ArrayList<>();

        Token token = tokens.remove(0);
        if (!token.getValue().equals("{")) {
            throw parseError(token, "Expected {");
        }
        children.add(new Node(NodeType.SYMBOL, token.getValue()));

        token = tokens.get(0);
        while (token.getValue().equals("var")) {
            children.add(new VarDecParser().parse(tokens));
            token = tokens.get(0);
        }

        children.add(new StatementsParser().parse(tokens));

        token = tokens.remove(0);
        if (!token.getValue().equals("}")) {
            throw parseError(token, "Expected }");
        }
        children.add(new Node(NodeType.SYMBOL, token.getValue()));

        return new Node(NodeType.SUBROUTINE_BODY, children);
    }
}
