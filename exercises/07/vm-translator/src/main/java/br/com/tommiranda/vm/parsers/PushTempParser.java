package br.com.tommiranda.vm.parsers;

import java.util.List;

public class PushTempParser implements Parser {

    @Override
    public String parse(String fileName, List<String> tokens) {
        String position = tokens.get(2);

        if(position.equals("0")) {
            return "@R5" + System.lineSeparator() +
                   "D=M" + System.lineSeparator() +
                   "@SP" + System.lineSeparator() +
                   "AM=M+1" + System.lineSeparator() +
                   "A=A-1" + System.lineSeparator() +
                   "M=D";
        }

        return "@5" + System.lineSeparator() +
               "D=A" + System.lineSeparator() +
               "@" + position + System.lineSeparator() +
               "D=D+A" + System.lineSeparator() +
               "A=D" + System.lineSeparator() +
               "D=M" + System.lineSeparator() +
               "@SP" + System.lineSeparator() +
               "AM=M+1" + System.lineSeparator() +
               "A=A-1" + System.lineSeparator() +
               "M=D";
    }
}
