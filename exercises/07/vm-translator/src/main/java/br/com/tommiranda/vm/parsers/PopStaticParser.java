package br.com.tommiranda.vm.parsers;

import org.apache.commons.text.TextStringBuilder;

import java.util.List;

public class PopStaticParser implements Parser {

    @Override
    public String parse(String fileName, List<String> tokens, int lineNumber) {
        String position = tokens.get(2);

        TextStringBuilder builder = new TextStringBuilder();

        return builder.appendln("@SP")
                      .appendln("AM=M-1")
                      .appendln("D=M")
                      .appendln("@%s.%s", fileName, position)
                      .append("M=D")
                      .build();
    }
}
