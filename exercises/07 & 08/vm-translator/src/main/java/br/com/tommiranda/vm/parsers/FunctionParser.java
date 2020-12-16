package br.com.tommiranda.vm.parsers;

import org.apache.commons.text.TextStringBuilder;

import java.util.List;

public class FunctionParser implements Parser {

    @Override
    public String parse(String fileName, List<String> tokens, int lineNumber, String functionName) {
        TextStringBuilder builder = new TextStringBuilder();

        functionName = tokens.get(1);
        int numArgs = Integer.parseInt(tokens.get(2));

        builder.append("(%s)", functionName);

        for (int i = 0; i < numArgs; i++) {
            builder.appendNewLine()
                   .appendln("@SP")
                   .appendln("AM=M+1")
                   .appendln("A=A-1")
                   .append("M=0");
        }

        return builder.build();
    }
}
