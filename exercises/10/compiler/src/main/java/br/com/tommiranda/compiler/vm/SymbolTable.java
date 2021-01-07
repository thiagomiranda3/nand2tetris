package br.com.tommiranda.compiler.vm;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public final class SymbolTable {

    private static final Set<String> subroutines = new HashSet<>();
    private static final Set<String> classes = new HashSet<>();
    private static final Map<String, SymbolAttribute> classTable = new HashMap<>();
    private static final Map<String, SymbolAttribute> subroutineTable = new HashMap<>();
    private static String className;
    private static String actualSubroutineName;
    private static String actualSubroutineType;
    private static SubroutineKind actualSubroutineKind;
    private static int fieldIndex = 0;
    private static int staticIndex = 0;
    private static int argIndex = 0;
    private static int localIndex = 0;

    public static boolean addClass(String name) {
        if (classes.contains(name)) {
            return false;
        }

        classes.add(name);
        return true;
    }

    public static boolean addSubroutine(String name) {
        if (subroutines.contains(SymbolTable.getClassName() + "." + name)) {
            return false;
        }

        subroutines.add(SymbolTable.getClassName() + "." + name);
        return true;
    }

    public static boolean addClassSymbol(String symbol, String type, SymbolKind kind) {
        if (classTable.containsKey(symbol)) {
            return false;
        }

        int index = kind.equals(SymbolKind.FIELD) ? fieldIndex++ : staticIndex++;

        classTable.putIfAbsent(symbol, new SymbolAttribute(type, kind, index));
        return true;
    }

    public static boolean addSubroutineSymbol(String symbol, String type, SymbolKind kind) {
        if (subroutineTable.containsKey(symbol)) {
            return false;
        }

        int offset = actualSubroutineKind.equals(SubroutineKind.METHOD) ? 1 : 0;
        int index = kind.equals(SymbolKind.LOCAL) ? localIndex++ : staticIndex++;

        subroutineTable.putIfAbsent(symbol, new SymbolAttribute(type, kind, index + offset));
        return true;
    }

    public static SymbolAttribute getSymbol(String symbol) {
        if(classTable.containsKey(symbol)) {
            return classTable.get(symbol);
        }

        return subroutineTable.get(symbol);
    }

    public static boolean containsSymbol(String symbol) {
        return classTable.containsKey(symbol) || subroutineTable.containsKey(symbol);
    }

    public static long countSymbols(SymbolKind kind) {
        if (kind.equals(SymbolKind.FIELD) || kind.equals(SymbolKind.STATIC)) {
            return classTable.values()
                             .stream()
                             .filter(s -> s.getKind().equals(kind))
                             .count();
        } else {
            return subroutineTable.values()
                                  .stream()
                                  .filter(s -> s.getKind().equals(kind))
                                  .count();
        }
    }

    public static String getClassName() {
        return className;
    }

    public static void setClassName(String className) {
        SymbolTable.className = className;
    }

    public static void startSubroutine(String name, String type, SubroutineKind kind) {
        actualSubroutineName = name;
        actualSubroutineType = type;
        actualSubroutineKind = kind;
    }

    public static void endSubroutine(String name) {
        actualSubroutineName = null;
        actualSubroutineType = null;
        actualSubroutineKind = null;
        localIndex = 0;
        argIndex = 0;
        subroutineTable.clear();
    }

    public static String getActualSubroutineName() {
        return actualSubroutineName;
    }

    public static String getActualSubroutineType() {
        return actualSubroutineType;
    }

    public static SubroutineKind getActualSubroutineKind() {
        return actualSubroutineKind;
    }
}
