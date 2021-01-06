package br.com.tommiranda.compiler.ast;

import br.com.tommiranda.compiler.tokenizer.Token;

import java.util.ArrayList;
import java.util.List;

public final class Node {

    private final NodeType type;
    private String value;
    private int lineNumber;
    private List<Node> children = new ArrayList<>();

    public Node(Token token) {
        this.type = NodeType.fromToken(token.getType());
        this.value = token.getValue();
        this.lineNumber = token.getLineNumber();
    }

    public Node(NodeType type, List<Node> children) {
        this.type = type;
        this.children = children;
    }

    public NodeType getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public List<Node> getChildren() {
        return children;
    }
}
