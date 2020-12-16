package br.com.tommiranda.vm.parsers;

import br.com.tommiranda.vm.SegmentToAssemblyVar;
import org.apache.commons.text.TextStringBuilder;

import java.util.List;

public class PushSegmentParser implements Parser {

    @Override
    public String parse(String fileName, List<String> tokens, int lineNumber, String functionName) {
        String segment = tokens.get(1);
        String position = tokens.get(2);

        TextStringBuilder builder = new TextStringBuilder();

        return builder.appendln(SegmentToAssemblyVar.convertName(segment))
                      .appendln("D=M")
                      .appendln("@%s", position)
                      .appendln("A=D+A")
                      .appendln("D=M")
                      .appendln("@SP")
                      .appendln("AM=M+1")
                      .appendln("A=A-1")
                      .append("M=D")
                      .build();
    }
}
