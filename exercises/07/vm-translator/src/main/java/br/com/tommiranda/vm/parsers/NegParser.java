package br.com.tommiranda.vm.parsers;

import org.apache.commons.text.TextStringBuilder;

import java.util.List;

public class NegParser implements Parser {

    @Override
    public String parse(String fileName, List<String> tokens, int lineNumber) {
        TextStringBuilder builder = new TextStringBuilder();

        return builder.appendln("@SP")
                      .appendln("A=M-1")
                      .append("M=-M")
                      .build();
    }
}
