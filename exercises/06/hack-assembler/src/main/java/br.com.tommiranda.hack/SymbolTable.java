package br.com.tommiranda.hack;

import java.util.HashMap;
import java.util.Map;

public final class SymbolTable {

    private static final Map<String, String> table = initTable();

    private static Map<String, String> initTable() {
        final Map<String, String> table = new HashMap<>();

        table.put("SCREEN", "0100000000000000");
        table.put("KBD", "0110000000000000");
        table.put("SP", "0000000000000000");
        table.put("LCL", "0000000000000001");
        table.put("ARG", "0000000000000010");
        table.put("THIS", "0000000000000011");
        table.put("THAT", "0000000000000100");

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
