package br.com.tommiranda.compiler.parsers.structure;

import br.com.tommiranda.compiler.ast.Node;
import br.com.tommiranda.compiler.ast.NodeType;
import br.com.tommiranda.compiler.parsers.Elements;
import br.com.tommiranda.compiler.parsers.Parser;
import br.com.tommiranda.compiler.tokenizer.Token;
import br.com.tommiranda.compiler.tokenizer.TokenType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SubroutineDecParser implements Parser {

    @Override
    public Node parse(List<Token> tokens) {
        var children = new ArrayList<Node>();

        Token token = tokens.remove(0);
        if (!token.getValue().equals("constructor") && !token.getValue().equals("function") && !token.getValue().equals("method")) {
            throw parseError(token, "Expected constructor, function or method");
        }
        children.add(new Node(token));

        token = tokens.remove(0);
        if (!token.getValue().equals("void") && !Elements.isType(token.getValue()) && !token.getType().equals(TokenType.IDENTIFIER)) {
            throw parseError(token, "Expected void, primitive type or class name");
        }
        children.add(new Node(token));

        token = tokens.remove(0);
        if (!token.getType().equals(TokenType.IDENTIFIER)) {
            throw parseError(token, "Expected a type followed by a subroutine name");
        }
        children.add(new Node(token));

        token = tokens.remove(0);
        if (!token.getValue().equals("(")) {
            throw parseError(token, "Expected (");
        }
        children.add(new Node(token));

        token = tokens.get(0);
        if (token.getValue().equals(")")) {
            token = tokens.remove(0);
            children.add(new Node(NodeType.PARAMETER_LIST, Collections.emptyList()));
            children.add(new Node(token));
            children.add(new SubroutineBodyParser().parse(tokens));

            return new Node(NodeType.SUBROUTINE_DEC, children);
        }

        children.add(new ParameterListParser().parse(tokens));

        token = tokens.remove(0);
        if (!token.getValue().equals(")")) {
            throw parseError(token, "Expected ) or , in parameters list");
        }
        children.add(new Node(token));

        children.add(new SubroutineBodyParser().parse(tokens));

        return new Node(NodeType.SUBROUTINE_DEC, children);
    }
}
