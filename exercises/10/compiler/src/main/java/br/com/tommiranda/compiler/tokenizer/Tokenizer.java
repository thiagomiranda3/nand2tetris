package br.com.tommiranda.compiler.tokenizer;

import br.com.tommiranda.compiler.errors.SyntaxError;
import org.apache.commons.collections4.bidimap.DualHashBidiMap;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.regex.Pattern;

public class Tokenizer {

    private final Set<String> symbols = createSymbols();
    private final List<Token> tokens = new LinkedList<>();
    private final Deque<String> brackets = new ArrayDeque<>();
    private final DualHashBidiMap<String, String> delimiters = createDelimiters();

    private int lineNumber = 0;
    private boolean parseable = false;

    private Set<String> createSymbols() {
        var symbols = new HashSet<String>();

        symbols.add(".");
        symbols.add(",");
        symbols.add(";");
        symbols.add("+");
        symbols.add("-");
        symbols.add("*");
        symbols.add("/");
        symbols.add("&");
        symbols.add("|");
        symbols.add("<");
        symbols.add(">");
        symbols.add("=");
        symbols.add("~");

        return symbols;
    }

    private DualHashBidiMap<String, String> createDelimiters() {
        var delimiters = new DualHashBidiMap<String, String>();

        delimiters.put("(", ")");
        delimiters.put("[", "]");
        delimiters.put("{", "}");

        return delimiters;
    }

    public List<Token> tokenizeFile(Path path) throws IOException {
        var lines = Files.readAllLines(path);

        boolean insideComment = false;

        Pattern noComment = Pattern.compile("(/\\*.*?\\*/)|(//.*)");

        for (String line : lines) {
            lineNumber++;

            line = noComment.matcher(line)
                            .replaceAll("")
                            .trim();

            if (insideComment) {
                int endMultiline = line.indexOf("*/");
                if (endMultiline == -1) {
                    continue;
                }

                line = line.substring(endMultiline + 2);
                insideComment = false;
            }

            int iniMultiline = line.indexOf("/*");
            if (iniMultiline >= 0) {
                line = line.substring(0, iniMultiline);
                insideComment = true;
            }

            if (line.isBlank()) {
                continue;
            }

            tokens.addAll(tokenizeLine(line));
        }

        parseable = brackets.isEmpty() && !tokens.isEmpty();

        if (parseable) {
            return tokens;
        }

        throw new SyntaxError("Missing brackets: " + brackets);
    }

    private LinkedList<Token> tokenizeLine(String expr) {
        boolean readAsString = false;
        var strAcc = new StringBuilder();
        var tokenAcc = new StringBuilder();
        var newTokens = new LinkedList<Token>();

        for (int i = 0; i < expr.length(); i++) {
            String c = String.valueOf(expr.charAt(i));

            if (c.equals("\"")) {
                readAsString = !readAsString;

                strAcc.append(c);

                if (!readAsString) {
                    newTokens.add(createToken(strAcc.toString()));
                    strAcc = new StringBuilder();
                }
            } else if (readAsString) {
                strAcc.append(c);
            } else if (c.equals(" ") || c.equals("\t") || c.equals("\r") || c.equals("\n")) {
                if (!tokenAcc.isEmpty()) {
                    newTokens.add(createToken(tokenAcc.toString()));
                    tokenAcc = new StringBuilder();
                }
            } else if (delimiters.containsKey(c)) {
                brackets.add(c);
                if (!tokenAcc.isEmpty()) {
                    newTokens.add(createToken(tokenAcc.toString()));
                    tokenAcc = new StringBuilder();
                }
                newTokens.add(createToken(c));
            } else if (delimiters.containsValue(c)) {
                if (brackets.isEmpty() || !brackets.removeLast().equals(delimiters.getKey(c))) {
                    throw new SyntaxError("Unmatched delimiter " + delimiters.getKey(c));
                }

                if (!tokenAcc.isEmpty()) {
                    newTokens.add(createToken(tokenAcc.toString()));
                    tokenAcc = new StringBuilder();
                }

                newTokens.add(createToken(c));
            } else if (symbols.contains(c)) {
                if (!tokenAcc.isEmpty()) {
                    newTokens.add(createToken(tokenAcc.toString()));
                    tokenAcc = new StringBuilder();
                }

                newTokens.add(createToken(c));
            } else {
                tokenAcc.append(c);
            }
        }

        if (!tokenAcc.isEmpty()) {
            newTokens.add(createToken(tokenAcc.toString()));
        }

        if (!strAcc.isEmpty()) {
            throw new SyntaxError("Unclosed quotes error");
        }

        return newTokens;
    }

    private Token createToken(String value) {
        return new Token(value, lineNumber, TokenType.getType(value));
    }
}
