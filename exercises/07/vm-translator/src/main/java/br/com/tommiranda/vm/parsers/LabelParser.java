package br.com.tommiranda.vm.parsers;

import org.apache.commons.text.TextStringBuilder;

import java.util.List;

public class LabelParser implements Parser {

    @Override
    public String parse(String fileName, List<String> tokens, int lineNumber, String functionName) {
        String label = tokens.get(1);

        String labelName = fileName + ".";

        if(!functionName.isBlank()) {
            labelName += functionName + "$";
        }

        labelName += label;

        TextStringBuilder builder = new TextStringBuilder();

        return builder.append("(%s)", labelName).build();
    }
}
