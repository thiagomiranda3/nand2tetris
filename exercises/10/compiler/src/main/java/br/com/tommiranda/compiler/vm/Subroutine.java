package br.com.tommiranda.compiler.vm;

public class Subroutine {

    private final String className;
    private final String name;
    private final String type;
    private final SubroutineKind kind;

    private int callNumber;
    private String callClass;

    public Subroutine(String className, String name, String type, SubroutineKind kind) {
        this.className = className;
        this.name = name;
        this.type = type;
        this.kind = kind;
    }

    public String getClassName() {
        return className;
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

    public String getCallClass() {
        return callClass;
    }

    public void setCallClass(String callClass) {
        this.callClass = callClass;
    }

    public int getCallNumber() {
        return callNumber;
    }

    public void setCallNumber(int callNumber) {
        this.callNumber = callNumber;
    }
}
