package br.com.tommiranda.compiler.parsers.statements;

import br.com.tommiranda.compiler.ast.Node;
import br.com.tommiranda.compiler.ast.NodeType;
import br.com.tommiranda.compiler.parsers.Parser;
import br.com.tommiranda.compiler.tokenizer.Token;

import java.util.ArrayList;
import java.util.List;

public class StatementsParser implements Parser {

    @Override
    public Node parse(List<Token> tokens) {
        List<Node> children = new ArrayList<>();

        Token token = tokens.get(0);
        while(!token.getValue().equals("}")) {
            switch (token.getValue()) {
                case "let" -> children.add(new LetStatementParser().parse(tokens));
                case "if" -> children.add(new IfStatementParser().parse(tokens));
                case "while" -> children.add(new WhileStatementParser().parse(tokens));
                case "do" -> children.add(new DoStatementParser().parse(tokens));
                case "return" -> children.add(new ReturnStatementParser().parse(tokens));
                default -> throw parseError(token, "Expected statement(do, let, while, return or if)");
            }

            token = tokens.get(0);
        }

        return new Node(NodeType.STATEMENTS, children);
    }
}
