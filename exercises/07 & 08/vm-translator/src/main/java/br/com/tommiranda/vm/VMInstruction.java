package br.com.tommiranda.vm;

import java.util.List;

public class VMInstruction {

    private final Type type;
    private final List<String> tokens;

    public VMInstruction(Type type, List<String> tokens) {
        this.type = type;
        this.tokens = tokens;
    }

    public Type getType() {
        return type;
    }

    public List<String> getTokens() {
        return tokens;
    }

    public static enum Type {
        PUSH_SEGMENT,
        PUSH_CONSTANT,
        PUSH_POINTER,
        PUSH_STATIC,
        PUSH_TEMP,
        POP_SEGMENT,
        POP_CONSTANT,
        POP_POINTER,
        POP_STATIC,
        POP_TEMP,
        EQ,
        GT,
        LT,
        NEG,
        OR,
        AND,
        NOT,
        ADD,
        SUB;
    }
}
