package br.com.tommiranda.compiler.vm;

import br.com.tommiranda.compiler.errors.SyntaxError;

public enum SymbolKind {
    FIELD("this"),
    STATIC("static"),
    ARGUMENT("argument"),
    LOCAL("local");

    private String name;

    SymbolKind(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static SymbolKind getKind(String kind) {
        return switch (kind) {
            case "field" -> FIELD;
            case "static" -> STATIC;
            case "argument" -> ARGUMENT;
            case "local" -> LOCAL;
            default -> throw new SyntaxError("kind undefined");
        };
    }
}
