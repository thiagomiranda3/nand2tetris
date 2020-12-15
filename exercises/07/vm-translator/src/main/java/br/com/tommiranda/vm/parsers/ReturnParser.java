package br.com.tommiranda.vm.parsers;

import org.apache.commons.text.TextStringBuilder;

import java.util.List;

public class ReturnParser implements Parser {

    @Override
    public String parse(String fileName, List<String> tokens, int lineNumber, String functionName) {
        TextStringBuilder builder = new TextStringBuilder();

        return builder.appendln("@LCL")
                      .appendln("D=M")
                      .appendln("@R14")
                      .appendln("M=D")
                      
                      .build();
    }
}
