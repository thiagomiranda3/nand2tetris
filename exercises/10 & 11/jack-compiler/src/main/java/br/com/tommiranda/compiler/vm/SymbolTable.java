package br.com.tommiranda.compiler.vm;

import java.util.*;

public final class SymbolTable {

    private static final Set<String> classes = new HashSet<>();
    private static final Set<String> subroutines = new HashSet<>();
    private static final Map<String, Subroutine> subroutineTable = new HashMap<>();
    private static final List<Subroutine> subroutinesToVerify = new ArrayList<>();

    private final String className;
    private final Map<String, SymbolAttribute> classSymbolTable = new HashMap<>();
    private final Map<String, SymbolAttribute> subroutineSymbolTable = new HashMap<>();

    private int fieldIndex = 0;
    private int staticIndex = 0;
    private int argIndex = 0;
    private int localIndex = 0;
    private Subroutine actualSubroutine;

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
        if (classSymbolTable.containsKey(symbol)) {
            return false;
        }

        int index = kind.equals(SymbolKind.FIELD) ? fieldIndex++ : staticIndex++;

        classSymbolTable.putIfAbsent(symbol, new SymbolAttribute(type, kind, index));
        return true;
    }

    public boolean addSubroutineSymbol(String symbol, String type, SymbolKind kind) {
        if (subroutineSymbolTable.containsKey(symbol)) {
            return false;
        }

        int offset = actualSubroutine.getKind().equals(SubroutineKind.METHOD) ? 1 : 0;
        int index = kind.equals(SymbolKind.LOCAL) ? localIndex++ : offset + argIndex++;

        subroutineSymbolTable.putIfAbsent(symbol, new SymbolAttribute(type, kind, index));
        return true;
    }

    public SymbolAttribute getSymbol(String symbol) {
        if (classSymbolTable.containsKey(symbol)) {
            return classSymbolTable.get(symbol);
        }

        return subroutineSymbolTable.get(symbol);
    }

    public boolean containsSymbol(String symbol) {
        return classSymbolTable.containsKey(symbol) || subroutineSymbolTable.containsKey(symbol);
    }

    public long countSymbols(SymbolKind kind) {
        if (kind.equals(SymbolKind.FIELD) || kind.equals(SymbolKind.STATIC)) {
            return classSymbolTable.values()
                                   .stream()
                                   .filter(s -> s.getKind().equals(kind))
                                   .count();
        } else {
            return subroutineSymbolTable.values()
                                        .stream()
                                        .filter(s -> s.getKind().equals(kind))
                                        .count();
        }
    }

    public String getClassName() {
        return className;
    }

    public void startSubroutine(String className, String name, String type, SubroutineKind kind) {
        this.actualSubroutine = new Subroutine(className, name, type, kind);

        subroutineTable.put(className + "." + name, actualSubroutine);
    }

    public void endSubroutine() {
        this.actualSubroutine = null;
        localIndex = 0;
        argIndex = 0;
        subroutineSymbolTable.clear();
    }

    public Subroutine getActualSubroutine() {
        return actualSubroutine;
    }

    public static Map<String, Subroutine> getSubroutineTable() {
        return subroutineTable;
    }

    public static List<Subroutine> getSubroutinesToVerify() {
        return subroutinesToVerify;
    }

    public static void addSubroutineToVerify(Subroutine subroutine) {
        subroutinesToVerify.add(subroutine);
    }
}
