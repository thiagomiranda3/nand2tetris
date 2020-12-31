package br.com.tommiranda.compiler.tokenizer;

public class Token {

    private final String value;
    private final int lineNumber;
    private final TokenType type;

    public Token(String value, int lineNumber, TokenType type) {
        this.value = value;
        this.lineNumber = lineNumber;
        this.type = type;
    }

    public String getValue() {
        return value;
    }

    public int getLineNumber() {
        return lineNumber;
    }

    public TokenType getType() {
        return type;
    }
}
