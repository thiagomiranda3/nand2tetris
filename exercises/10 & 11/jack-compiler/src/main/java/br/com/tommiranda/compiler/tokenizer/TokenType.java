package br.com.tommiranda.compiler.tokenizer;

import br.com.tommiranda.compiler.parsers.Elements;
import org.apache.commons.lang3.math.NumberUtils;

public enum TokenType {
    KEYWORD, IDENTIFIER, SYMBOL, INTEGER, STRING;

    public static TokenType getType(String value) {
        if (Elements.isKeyword(value)) {
            return KEYWORD;
        }

        if (Elements.isSymbol(value)) {
            return SYMBOL;
        }

        if (NumberUtils.isDigits(value)) {
            return INTEGER;
        }

        if (value.startsWith("\"")) {
            return STRING;
        }

        return IDENTIFIER;
    }
}
