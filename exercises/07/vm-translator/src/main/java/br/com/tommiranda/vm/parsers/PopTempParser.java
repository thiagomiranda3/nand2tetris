package br.com.tommiranda.vm.parsers;

import org.apache.commons.text.TextStringBuilder;

import java.util.List;

public class PopTempParser implements Parser {

    @Override
    public String parse(String fileName, List<String> tokens, int lineNumber, String functionName) {
        String position = tokens.get(2);

        TextStringBuilder builder = new TextStringBuilder();

        if (position.equals("0")) {
            return builder.appendln("@SP")
                          .appendln("AM=M-1")
                          .appendln("D=M")
                          .appendln("@R5")
                          .append("M=D")
                          .build();
        }

        return builder.appendln("@5")
                      .appendln("D=A")
                      .appendln("@%s", position)
                      .appendln("D=D+A")
                      .appendln("@R13")
                      .appendln("M=D")
                      .appendln("@SP")
                      .appendln("AM=M-1")
                      .appendln("D=M")
                      .appendln("@R13")
                      .appendln("A=M")
                      .append("M=D")
                      .build();
    }
}
