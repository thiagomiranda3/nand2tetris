package br.com.tommiranda.compiler.ast;

import java.util.List;

public final class Node {

    private final NodeType type;
    private String value;
    private List<Node> children;

    public Node(NodeType type, List<Node> children) {
        this.type = type;
        this.children = children;
    }

    public Node(NodeType type, String value) {
        this.type = type;
        this.value = value;
    }

    public NodeType getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    public List<Node> getChildren() {
        return children;
    }
}
