package br.com.tommiranda.vm.parsers;

import br.com.tommiranda.vm.SegmentToAssemblyVar;

import java.util.List;

public class PushSegmentParser implements Parser {

    @Override
    public String parse(String fileName, List<String> tokens) {
        String segment = tokens.get(1);
        String position = tokens.get(2);

        return SegmentToAssemblyVar.convertName(segment) + System.lineSeparator() +
               "D=M" + System.lineSeparator() +
               "@" + position + System.lineSeparator() +
               "A=D+A" + System.lineSeparator() +
               "D=M" + System.lineSeparator() +
               "@SP" + System.lineSeparator() +
               "AM=M+1" + System.lineSeparator() +
               "A=A-1" + System.lineSeparator() +
               "M=D";
    }
}
