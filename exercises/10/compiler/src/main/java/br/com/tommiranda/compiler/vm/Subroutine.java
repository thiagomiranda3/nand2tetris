package br.com.tommiranda.compiler.vm;

public class Subroutine {

    private final String name;
    private final String type;
    private final SubroutineKind kind;

    public Subroutine(String name, String type, SubroutineKind kind) {
        this.name = name;
        this.type = type;
        this.kind = kind;
    }

    public String getName() {
        return name;
    }

    public String getType() {
        return type;
    }

    public SubroutineKind getKind() {
        return kind;
    }
}
