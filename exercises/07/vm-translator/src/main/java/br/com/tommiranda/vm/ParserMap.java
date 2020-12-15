package br.com.tommiranda.vm;

import br.com.tommiranda.vm.parsers.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class ParserMap {

    public static Map<String, Parser> parsers = initTypes();

    private static Map<String, Parser> initTypes() {
        Map<String, Parser> types = new HashMap<>();

        types.put("push local", new PushSegmentParser());
        types.put("push argument", new PushSegmentParser());
        types.put("push this", new PushSegmentParser());
        types.put("push that", new PushSegmentParser());
        types.put("push constant", new PushConstantParser());
        types.put("push pointer", new PushPointerParser());
        types.put("push static", new PushStaticParser());
        types.put("push temp", new PushTempParser());
        types.put("pop local", new PopSegmentParser());
        types.put("pop argument", new PopSegmentParser());
        types.put("pop this", new PopSegmentParser());
        types.put("pop that", new PopSegmentParser());
        types.put("pop pointer", new PopPointerParser());
        types.put("pop static", new PopStaticParser());
        types.put("pop temp", new PopTempParser());

        types.put("eq", new EqParser());
        types.put("gt", new GtParser());
        types.put("lt", new LtParser());
        types.put("neg", new NegParser());
        types.put("or", new OrParser());
        types.put("and", new AndParser());
        types.put("not", new NotParser());
        types.put("add", new AddParser());
        types.put("sub", new SubParser());

        types.put("if-goto", new IfGotoParser());
        types.put("label", new LabelParser());
        types.put("goto", new GotoParser());

        types.put("function", new FunctionParser());
        types.put("call", new CallParser());
        types.put("return", new ReturnParser());


        return types;
    }

    public static Parser getParser(List<String> tokens) {
        String firstToken = tokens.get(0);

        if (firstToken.equals("push") || firstToken.equals("pop")) {
            String key = firstToken + " " + tokens.get(1);

            return parsers.get(key);
        }

        return parsers.get(firstToken);
    }
}
