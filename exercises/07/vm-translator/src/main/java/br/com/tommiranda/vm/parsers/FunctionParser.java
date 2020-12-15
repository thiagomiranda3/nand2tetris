package br.com.tommiranda.vm.parsers;

import org.apache.commons.text.TextStringBuilder;

import java.util.List;

public class FunctionParser implements Parser {

    @Override
    public String parse(String fileName, List<String> tokens, int lineNumber, String functionName) {
        TextStringBuilder builder = new TextStringBuilder();

        functionName = tokens.get(1);
        int numArgs = Integer.parseInt(tokens.get(2));

        builder.appendln("(%s.%s)", fileName, functionName);

        for (int i = 0; i < numArgs; i++) {
            builder.appendln("@SP")
                   .appendln("AM=M+1")
                   .appendln("A=A-1")
                   .appendln("M=0");
        }

        return builder.build();
    }
}
