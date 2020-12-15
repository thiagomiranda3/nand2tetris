package br.com.tommiranda.vm.parsers;

import org.apache.commons.text.TextStringBuilder;

import java.util.List;

public class SubParser implements Parser {

    @Override
    public String parse(String fileName, List<String> tokens, int lineNumber, String functionName) {
        TextStringBuilder builder = new TextStringBuilder();

        return builder.appendln("@SP")
                      .appendln("AM=M-1")
                      .appendln("D=M")
                      .appendln("A=A-1")
                      .append("M=M-D")
                      .build();
    }
}
