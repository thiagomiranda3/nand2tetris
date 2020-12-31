package br.com.tommiranda.compiler.parsers;

import br.com.tommiranda.compiler.ast.Node;
import br.com.tommiranda.compiler.errors.SyntaxError;
import br.com.tommiranda.compiler.tokenizer.Token;

import java.util.List;

public interface Parser {

    Node parse(List<Token> tokens);

    default SyntaxError parseError(Token token, String message) {
        return new SyntaxError("(line " + token.getLineNumber() + "): " + message);
    }
}
