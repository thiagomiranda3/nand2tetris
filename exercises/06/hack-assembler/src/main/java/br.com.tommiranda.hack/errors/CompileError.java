package br.com.tommiranda.hack.errors;

public class CompileError extends RuntimeException {

    public CompileError(String message) {
        super(message);
    }
}
