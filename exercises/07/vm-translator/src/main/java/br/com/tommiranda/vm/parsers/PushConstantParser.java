package br.com.tommiranda.vm.parsers;

import org.apache.commons.text.TextStringBuilder;

import java.util.List;

public class PushConstantParser implements Parser {

    @Override
    public String parse(String fileName, List<String> tokens, int lineNumber) {
        String value = tokens.get(2);

        TextStringBuilder builder = new TextStringBuilder();

        return builder.appendln("@%s", value)
                      .appendln("D=A")
                      .appendln("@SP")
                      .appendln("AM=M+1")
                      .appendln("A=A-1")
                      .append("M=D")
                      .build();
    }
}
