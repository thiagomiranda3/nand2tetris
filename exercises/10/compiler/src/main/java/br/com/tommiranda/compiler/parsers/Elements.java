package br.com.tommiranda.compiler.parsers;

import java.util.HashSet;
import java.util.Set;

public class Elements {

    private static final Set<String> keywords = createKeywords();
    private static final Set<String> types = createTypes();
    private static final Set<String> symbols = createSymbols();
    private static final Set<String> ops = createOps();
    private static final Set<String> unaryOps = createUnaryOps();
    private static final Set<String> constants = createConstants();

    private static Set<String> createKeywords() {
        Set<String> keywords = new HashSet<>();

        keywords.add("class");
        keywords.add("constructor");
        keywords.add("function");
        keywords.add("method");
        keywords.add("field");
        keywords.add("static");
        keywords.add("var");
        keywords.add("int");
        keywords.add("char");
        keywords.add("boolean");
        keywords.add("void");
        keywords.add("true");
        keywords.add("false");
        keywords.add("null");
        keywords.add("this");
        keywords.add("let");
        keywords.add("do");
        keywords.add("if");
        keywords.add("else");
        keywords.add("while");
        keywords.add("return");

        return keywords;
    }

    private static Set<String> createTypes() {
        Set<String> types = new HashSet<>();

        types.add("int");
        types.add("char");
        types.add("boolean");

        return types;
    }

    private static Set<String> createSymbols() {
        Set<String> symbols = new HashSet<>();

        symbols.add("{");
        symbols.add("}");
        symbols.add("(");
        symbols.add(")");
        symbols.add("[");
        symbols.add("]");
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

    private static Set<String> createOps() {
        Set<String> ops = new HashSet<>();

        ops.add("+");
        ops.add("-");
        ops.add("*");
        ops.add("/");
        ops.add("&");
        ops.add("|");
        ops.add("<");
        ops.add(">");
        ops.add("=");

        return ops;
    }

    private static Set<String> createUnaryOps() {
        Set<String> unaryOps = new HashSet<>();

        unaryOps.add("-");
        unaryOps.add("~");

        return unaryOps;
    }

    private static Set<String> createConstants() {
        Set<String> constants = new HashSet<>();

        constants.add("true");
        constants.add("false");
        constants.add("null");
        constants.add("this");

        return constants;
    }

    public static boolean isKeyword(String keyword) {
        return keywords.contains(keyword);
    }

    public static boolean isType(String type) {
        return types.contains(type);
    }

    public static boolean isSymbol(String symbol) {
        return symbols.contains(symbol);
    }

    public static boolean isOp(String op) {
        return ops.contains(op);
    }

    public static boolean isUnaryOp(String unaryOp) {
        return unaryOps.contains(unaryOp);
    }

    public static boolean isConstant(String constant) {
        return constants.contains(constant);
    }
}
