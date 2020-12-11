package br.com.tommiranda.vm.parsers;

import java.util.List;

public class PushConstantParser implements Parser {

    @Override
    public String parse(String fileName, List<String> tokens) {
        String value = tokens.get(2);

        return "@" + value + System.lineSeparator() +
               "D=A" + System.lineSeparator() +
               "@SP" + System.lineSeparator() +
               "AM=M+1" + System.lineSeparator() +
               "A=A-1" + System.lineSeparator() +
               "M=D";
    }
}
