package br.com.tommiranda.vm.parsers;

import org.apache.commons.text.TextStringBuilder;

import java.util.List;

public class GotoParser implements Parser {

    @Override
    public String parse(String fileName, List<String> tokens, int lineNumber, String functionName) {
        String label = tokens.get(1);

        String labelName = "";

        if(!functionName.isBlank()) {
            labelName += functionName + "$";
        }

        labelName += label;

        TextStringBuilder builder = new TextStringBuilder();

        return builder.appendln("@%s", labelName)
                      .append("0;JMP")
                      .build();
    }
}
