package br.com.tommiranda.compiler.vm;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public final class SymbolTable {

    private static final Set<String> classes = new HashSet<>();
    private static final Set<String> subroutines = new HashSet<>();
    private final String className;
    private final Map<String, SymbolAttribute> classTable = new HashMap<>();
    private final Map<String, SymbolAttribute> subroutineTable = new HashMap<>();
    private String actualSubroutineName;
    private String actualSubroutineType;
    private SubroutineKind actualSubroutineKind;
    private int fieldIndex = 0;
    private int staticIndex = 0;
    private int argIndex = 0;
    private int localIndex = 0;

    public SymbolTable(String className) {
        this.className = className;
    }

    public static boolean addClass(String name) {
        if (classes.contains(name)) {
            return false;
        }

        classes.add(name);
        return true;
    }

    public static boolean addSubroutine(String className, String name) {
        if (subroutines.contains(className + "." + name)) {
            return false;
        }

        subroutines.add(className + "." + name);
        return true;
    }

    public static boolean containsSubroutine(String className, String name) {
        return subroutines.contains(className + "." + name);
    }

    public boolean addClassSymbol(String symbol, String type, SymbolKind kind) {
        if (classTable.containsKey(symbol)) {
            return false;
        }

        int index = kind.equals(SymbolKind.FIELD) ? fieldIndex++ : staticIndex++;

        classTable.putIfAbsent(symbol, new SymbolAttribute(type, kind, index));
        return true;
    }

    public boolean addSubroutineSymbol(String symbol, String type, SymbolKind kind) {
        if (subroutineTable.containsKey(symbol)) {
            return false;
        }

        int offset = actualSubroutineKind.equals(SubroutineKind.METHOD) ? 1 : 0;
        int index = kind.equals(SymbolKind.LOCAL) ? localIndex++ : offset + argIndex++;

        subroutineTable.putIfAbsent(symbol, new SymbolAttribute(type, kind, index));
        return true;
    }

    public SymbolAttribute getSymbol(String symbol) {
        if (classTable.containsKey(symbol)) {
            return classTable.get(symbol);
        }

        return subroutineTable.get(symbol);
    }

    public boolean containsSymbol(String symbol) {
        return classTable.containsKey(symbol) || subroutineTable.containsKey(symbol);
    }

    public long countSymbols(SymbolKind kind) {
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

    public String getClassName() {
        return className;
    }

    public void startSubroutine(String name, String type, SubroutineKind kind) {
        actualSubroutineName = name;
        actualSubroutineType = type;
        actualSubroutineKind = kind;
    }

    public void endSubroutine() {
        actualSubroutineName = null;
        actualSubroutineType = null;
        actualSubroutineKind = null;
        localIndex = 0;
        argIndex = 0;
        subroutineTable.clear();
    }

    public String getActualSubroutineName() {
        return actualSubroutineName;
    }

    public String getActualSubroutineType() {
        return actualSubroutineType;
    }

    public SubroutineKind getActualSubroutineKind() {
        return actualSubroutineKind;
    }
}
