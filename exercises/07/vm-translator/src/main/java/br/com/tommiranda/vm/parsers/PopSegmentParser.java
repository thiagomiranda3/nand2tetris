package br.com.tommiranda.vm.parsers;

import br.com.tommiranda.vm.SegmentToAssemblyVar;

import java.util.List;

public class PopSegmentParser implements Parser {

    @Override
    public String parse(List<String> tokens) {
        String segment = tokens.get(1);
        String position = tokens.get(2);

        return SegmentToAssemblyVar.convertName(segment) + System.lineSeparator() +
               "D=M" + System.lineSeparator() +
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
