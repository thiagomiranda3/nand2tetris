package br.com.tommiranda.vm.parsers;

import org.apache.commons.text.TextStringBuilder;

import java.util.List;

public class PushTempParser implements Parser {

    @Override
    public String parse(String fileName, List<String> tokens, int lineNumber, String functionName) {
        String position = tokens.get(2);

        TextStringBuilder builder = new TextStringBuilder();

        if (position.equals("0")) {
            return builder.appendln("@R5")
                          .appendln("D=M")
                          .appendln("@SP")
                          .appendln("AM=M+1")
                          .appendln("A=A-1")
                          .append("M=D")
                          .build();
        }

        return builder.appendln("@5")
                      .appendln("D=A")
                      .appendln("@%s", position)
                      .appendln("D=D+A")
                      .appendln("A=D")
                      .appendln("D=M")
                      .appendln("@SP")
                      .appendln("AM=M+1")
                      .appendln("A=A-1")
                      .append("M=D")
                      .build();
    }
}
