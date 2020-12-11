package br.com.tommiranda.vm.parsers;

import java.util.List;

public class PopTempParser implements Parser {

    @Override
    public String parse(String fileName, List<String> tokens) {
        String position = tokens.get(2);

        if(position.equals("0")) {
            return "@SP" + System.lineSeparator() +
                   "AM=M-1" + System.lineSeparator() +
                   "D=M" + System.lineSeparator() +
                   "@R5" + System.lineSeparator() +
                   "M=D";
        }

        return "@5" + System.lineSeparator() +
               "D=A" + System.lineSeparator() +
               "@" + position + System.lineSeparator() +
               "D=D+A" + System.lineSeparator() +
               "@R13" + System.lineSeparator() +
               "M=D" + System.lineSeparator() +
               "@SP" + System.lineSeparator() +
               "AM=M-1" + System.lineSeparator() +
               "D=M" + System.lineSeparator() +
               "@R13" + System.lineSeparator() +
               "A=M" + System.lineSeparator() +
               "M=D";
    }
}
