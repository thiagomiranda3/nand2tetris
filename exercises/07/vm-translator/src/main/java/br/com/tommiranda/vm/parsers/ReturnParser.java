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
                      .appendln("@5")
                      .appendln("D=D-A")
                      .appendln("A=D")
                      .appendln("D=M")
                      .appendln("@returnAddress")
                      .appendln("M=D")
                      .appendln("@SP")
                      .appendln("AM=M-1")
                      .appendln("D=M")
                      .appendln("@ARG")
                      .appendln("A=M")
                      .appendln("M=D")
                      .appendln("@ARG")
                      .appendln("D=M+1")
                      .appendln("@SP")
                      .appendln("M=D")
                      .appendln("@R14")
                      .appendln("A=M-1")
                      .appendln("D=M")
                      .appendln("@THAT")
                      .appendln("M=D")
                      .appendln("@2")
                      .appendln("D=A")
                      .appendln("@R14")
                      .appendln("A=M-D")
                      .appendln("D=M")
                      .appendln("@THIS")
                      .appendln("M=D")
                      .appendln("@3")
                      .appendln("D=A")
                      .appendln("@R14")
                      .appendln("A=M-D")
                      .appendln("D=M")
                      .appendln("@ARG")
                      .appendln("M=D")
                      .appendln("@4")
                      .appendln("D=A")
                      .appendln("@R14")
                      .appendln("A=M-D")
                      .appendln("D=M")
                      .appendln("@LCL")
                      .appendln("M=D")
                      .appendln("@returnAddress")
                      .appendln("A=M")
                      .append("0;JMP")
                      .build();
    }
}
