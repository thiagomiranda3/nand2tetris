package br.com.tommiranda.vm.parsers;

import br.com.tommiranda.vm.errors.TranslationException;
import org.apache.commons.text.TextStringBuilder;

import java.util.List;

public class PopPointerParser implements Parser {

    @Override
    public String parse(String fileName, List<String> tokens, int lineNumber) {
        String value = tokens.get(2);

        if (!value.equals("0") && !value.equals("1")) {
            throw new TranslationException("pointer only accepts value 0 or 1");
        }

        String type = value.equals("0") ? "@THIS" : "@THAT";

        TextStringBuilder builder = new TextStringBuilder();

        return builder.appendln("@SP")
                      .appendln("AM=M-1")
                      .appendln("D=M")
                      .appendln(type)
                      .append("M=D")
                      .build();
    }
}
