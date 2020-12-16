package br.com.tommiranda.vm.parsers;

import org.apache.commons.text.TextStringBuilder;

import java.util.List;

public class GtParser implements Parser {

    @Override
    public String parse(String fileName, List<String> tokens, int lineNumber, String functionName) {
        TextStringBuilder builder = new TextStringBuilder();

        return builder.appendln("@SP")
                      .appendln("AM=M-1")
                      .appendln("D=M")
                      .appendln("A=A-1")
                      .appendln("D=M-D;")
                      .appendln("@EQ_%d", lineNumber)
                      .appendln("D;JGT")
                      .appendln("@SP")
                      .appendln("A=M-1")
                      .appendln("M=0")
                      .appendln("@END_%d", lineNumber)
                      .appendln("0;JMP")
                      .appendln("(EQ_%d)", lineNumber)
                      .appendln("@SP")
                      .appendln("A=M-1")
                      .appendln("M=-1")
                      .append("(END_%d)", lineNumber)
                      .build();
    }
}
