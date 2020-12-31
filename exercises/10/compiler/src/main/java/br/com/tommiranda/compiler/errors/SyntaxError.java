package br.com.tommiranda.compiler.errors;

public class SyntaxError extends RuntimeException {

    public SyntaxError(String message) {
        super(message);
    }
}
