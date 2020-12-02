package br.com.tommiranda.hack;

import java.util.HashMap;
import java.util.Map;

public final class SymbolTable {

    private static final Map<String, String> table = initTable();

    private static Map<String, String> initTable() {
        final Map<String, String> table = new HashMap<>();

        table.put("SCREEN", "0100000000000000");

        return table;
    }

    public static void add(String symbol, String value) {
        table.put(symbol, value);
    }

    public static String get(String symbol) {
        return table.get(symbol);
    }

    public static boolean contains(String symbol) {
        return table.containsKey(symbol);
    }
}
