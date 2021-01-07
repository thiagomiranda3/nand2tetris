package br.com.tommiranda.compiler.vm;

public class SymbolAttribute {

    private final String type;
    private final SymbolKind kind;
    private final int index;

    public SymbolAttribute(String type, SymbolKind kind, int index) {
        this.type = type;
        this.kind = kind;
        this.index = index;
    }

    public String getType() {
        return type;
    }

    public SymbolKind getKind() {
        return kind;
    }

    public int getIndex() {
        return index;
    }
}
