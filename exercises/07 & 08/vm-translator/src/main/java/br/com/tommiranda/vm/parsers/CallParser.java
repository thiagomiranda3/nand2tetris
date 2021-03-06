package br.com.tommiranda.vm.parsers;

import org.apache.commons.text.TextStringBuilder;

import java.util.List;

public class CallParser implements Parser {

    @Override
    public String parse(String fileName, List<String> tokens, int lineNumber, String functionName) {
        String functionCall = tokens.get(1);
        int numArgs = Integer.parseInt(tokens.get(2));

        String returnAddress = functionCall + "$ret." + lineNumber;

        TextStringBuilder builder = new TextStringBuilder();

        return builder.appendln("@%s", returnAddress)
                      .appendln("D=A")
                      .appendln("@SP")
                      .appendln("AM=M+1")
                      .appendln("A=A-1")
                      .appendln("M=D")
                      .appendln("@LCL")
                      .appendln("D=M")
                      .appendln("@SP")
                      .appendln("AM=M+1")
                      .appendln("A=A-1")
                      .appendln("M=D")
                      .appendln("@ARG")
                      .appendln("D=M")
                      .appendln("@SP")
                      .appendln("AM=M+1")
                      .appendln("A=A-1")
                      .appendln("M=D")
                      .appendln("@THIS")
                      .appendln("D=M")
                      .appendln("@SP")
                      .appendln("AM=M+1")
                      .appendln("A=A-1")
                      .appendln("M=D")
                      .appendln("@THAT")
                      .appendln("D=M")
                      .appendln("@SP")
                      .appendln("AM=M+1")
                      .appendln("A=A-1")
                      .appendln("M=D")
                      .appendln("@SP")
                      .appendln("D=M")
                      .appendln("@%d", numArgs)
                      .appendln("D=D-A")
                      .appendln("@5")
                      .appendln("D=D-A")
                      .appendln("@ARG")
                      .appendln("M=D")
                      .appendln("@SP")
                      .appendln("D=M")
                      .appendln("@LCL")
                      .appendln("M=D")
                      .appendln("@%s", functionCall)
                      .appendln("0;JMP")
                      .append("(%s)", returnAddress)
                      .build();
    }
}
