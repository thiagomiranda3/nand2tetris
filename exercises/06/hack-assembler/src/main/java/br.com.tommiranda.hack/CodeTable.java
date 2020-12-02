package br.com.tommiranda.hack;

import java.util.HashMap;
import java.util.Map;

public class CodeTable {

    private static final Map<String, String> table = initTable();

    private static Map<String, String> initTable() {
        final Map<String, String> table = new HashMap<>();

        return table;
    }

    public static void add(String code, String value) {
        table.put(code, value);
    }

    public static String get(String code) {
        return table.get(code);
    }

    public static boolean contains(String code) {
        return table.containsKey(code);
    }
}
