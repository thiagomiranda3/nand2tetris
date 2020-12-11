package br.com.tommiranda.vm.parsers;

import java.util.List;

public class PopStaticParser implements Parser {

    @Override
    public String parse(String fileName, List<String> tokens) {
        String position = tokens.get(2);

        return "@SP" + System.lineSeparator() +
               "AM=M-1" + System.lineSeparator() +
               "D=M" + System.lineSeparator() +
               "@" + fileName + "." + position + System.lineSeparator() +
               "M=D";
    }
}
