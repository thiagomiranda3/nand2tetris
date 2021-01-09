package br.com.tommiranda.compiler.vm;

import br.com.tommiranda.compiler.errors.SyntaxError;

public enum SubroutineKind {
    METHOD("method"),
    FUNCTION("function"),
    CONSTRUCTOR("constructor");

    private String name;

    SubroutineKind(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static SubroutineKind getKind(String kind) {
        return switch (kind) {
            case "method" -> METHOD;
            case "function" -> FUNCTION;
            case "constructor" -> CONSTRUCTOR;
            default -> throw new SyntaxError("kind undefined");
        };
    }
}
