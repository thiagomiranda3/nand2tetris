package br.com.tommiranda.vm.parsers;

import java.util.List;

public class NegParser implements Parser {

    @Override
    public String parse(String fileName, List<String> tokens) {
        return "@SP" + System.lineSeparator() +
               "A=M-1" + System.lineSeparator() +
               "M=!M";
    }
}
