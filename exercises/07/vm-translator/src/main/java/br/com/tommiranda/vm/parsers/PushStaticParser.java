package br.com.tommiranda.vm.parsers;

import java.util.List;

public class PushStaticParser implements Parser {

    @Override
    public String parse(String fileName, List<String> tokens) {
        String position = tokens.get(2);

        return "@" + fileName + "." + position + System.lineSeparator() +
               "D=M" + System.lineSeparator() +
               "@SP" + System.lineSeparator() +
               "AM=M+1" + System.lineSeparator() +
               "A=A-1" + System.lineSeparator() +
               "M=D";
    }
}
