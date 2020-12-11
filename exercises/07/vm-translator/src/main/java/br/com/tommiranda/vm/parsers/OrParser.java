package br.com.tommiranda.vm.parsers;

import java.util.List;

public class OrParser implements Parser {

    @Override
    public String parse(String fileName, List<String> tokens) {
        return "@SP" + System.lineSeparator() +
               "AM=M-1" + System.lineSeparator() +
               "D=M" + System.lineSeparator() +
               "A=A-1" + System.lineSeparator() +
               "M=D|M";
    }
}
