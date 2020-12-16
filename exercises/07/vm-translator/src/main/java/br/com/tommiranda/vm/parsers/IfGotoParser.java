package br.com.tommiranda.vm.parsers;

import org.apache.commons.text.TextStringBuilder;

import java.util.List;

public class IfGotoParser implements Parser {

    @Override
    public String parse(String fileName, List<String> tokens, int lineNumber, String functionName) {
        String label = tokens.get(1);

        String labelName = "";

        if(!functionName.isBlank()) {
            labelName += functionName + "$";
        }

        labelName += label;

        TextStringBuilder builder = new TextStringBuilder();

        return builder.appendln("@SP")
                      .appendln("AM=M-1")
                      .appendln("D=M")
                      .appendln("@%s", labelName)
                      .append("D;JNE")
                      .build();
    }
}
