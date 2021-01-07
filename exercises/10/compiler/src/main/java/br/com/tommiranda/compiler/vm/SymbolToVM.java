package br.com.tommiranda.compiler.vm;

import java.util.HashMap;
import java.util.Map;

public final class SymbolToVM {

    private static final Map<String, String> table = createTable();

    private static Map<String, String> createTable() {
        Map<String, String> table = new HashMap<>();

        table.put("+", "add");
        table.put("-", "sub");
        table.put("~", "not");
        table.put("=", "eq");
        table.put(">", "gt");
        table.put("<", "lt");
        table.put("&", "and");
        table.put("|", "or");
        table.put("*", "call Math.multiply 2");
        table.put("/", "call Math.divide 2");

        return table;
    }

    public static String convert(String symbol) {
        return table.get(symbol);
    }
}
