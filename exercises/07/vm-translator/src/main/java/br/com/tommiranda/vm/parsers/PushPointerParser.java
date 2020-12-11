package br.com.tommiranda.vm.parsers;

import br.com.tommiranda.vm.errors.TranslationException;

import java.util.List;

public class PushPointerParser implements Parser {

    @Override
    public String parse(String fileName, List<String> tokens) {
        String value = tokens.get(2);

        if(!value.equals("0") && !value.equals("1")) {
            throw new TranslationException("pointer only accepts value 0 or 1");
        }

        String type = value.equals("0") ? "@THIS" : "@THAT";

        return type + System.lineSeparator() +
               "D=M" + System.lineSeparator() +
               "@SP" + System.lineSeparator() +
               "AM=M+1" + System.lineSeparator() +
               "A=A-1" + System.lineSeparator() +
               "M=D";
    }
}
