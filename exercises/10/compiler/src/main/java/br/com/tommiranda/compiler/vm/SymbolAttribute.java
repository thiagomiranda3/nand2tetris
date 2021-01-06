package br.com.tommiranda.compiler.vm;

public class SymbolAttribute {

    private final String type;
    private final String kind;
    private final int index;

    public SymbolAttribute(String type, String kind, int index) {
        this.type = type;
        this.kind = kind;
        this.index = index;
    }

    public String getType() {
        return type;
    }

    public String getKind() {
        return kind;
    }

    public int getIndex() {
        return index;
    }
}
