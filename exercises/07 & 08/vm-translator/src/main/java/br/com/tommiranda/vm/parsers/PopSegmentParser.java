package br.com.tommiranda.vm.parsers;

import br.com.tommiranda.vm.SegmentToAssemblyVar;
import org.apache.commons.text.TextStringBuilder;

import java.util.List;

public class PopSegmentParser implements Parser {

    @Override
    public String parse(String fileName, List<String> tokens, int lineNumber, String functionName) {
        String segment = tokens.get(1);
        String position = tokens.get(2);

        TextStringBuilder builder = new TextStringBuilder();

        return builder.appendln(SegmentToAssemblyVar.convertName(segment))
                      .appendln("D=M")
                      .appendln("@%s", position)
                      .appendln("D=D+A")
                      .appendln("@R13")
                      .appendln("M=D")
                      .appendln("@SP")
                      .appendln("AM=M-1")
                      .appendln("D=M")
                      .appendln("@R13")
                      .appendln("A=M")
                      .append("M=D")
                      .build();
    }
}
