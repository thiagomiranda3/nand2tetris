package br.com.tommiranda.vm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InstructionType {

    public static Map<String, VMInstruction.Type> types = initTypes();

    private static Map<String, VMInstruction.Type> initTypes() {
        Map<String, VMInstruction.Type> types = new HashMap<>();

        types.put("push local", VMInstruction.Type.PUSH_SEGMENT);
        types.put("push argument", VMInstruction.Type.PUSH_SEGMENT);
        types.put("push this", VMInstruction.Type.PUSH_SEGMENT);
        types.put("push that", VMInstruction.Type.PUSH_SEGMENT);
        types.put("push constant", VMInstruction.Type.PUSH_CONSTANT);
        types.put("push pointer", VMInstruction.Type.PUSH_POINTER);
        types.put("push static", VMInstruction.Type.PUSH_STATIC);
        types.put("push temp", VMInstruction.Type.PUSH_TEMP);
        types.put("pop local", VMInstruction.Type.POP_SEGMENT);
        types.put("pop argument", VMInstruction.Type.POP_SEGMENT);
        types.put("pop this", VMInstruction.Type.POP_SEGMENT);
        types.put("pop that", VMInstruction.Type.POP_SEGMENT);
        types.put("pop constant", VMInstruction.Type.POP_CONSTANT);
        types.put("pop pointer", VMInstruction.Type.POP_POINTER);
        types.put("pop static", VMInstruction.Type.POP_STATIC);
        types.put("pop temp", VMInstruction.Type.POP_TEMP);

        types.put("eq", VMInstruction.Type.EQ);
        types.put("gt", VMInstruction.Type.GT);
        types.put("lt", VMInstruction.Type.LT);
        types.put("neg", VMInstruction.Type.NEG);
        types.put("or", VMInstruction.Type.OR);
        types.put("and", VMInstruction.Type.AND);
        types.put("not", VMInstruction.Type.NOT);
        types.put("add", VMInstruction.Type.ADD);
        types.put("sub", VMInstruction.Type.SUB);

        return types;
    }

    public static VMInstruction.Type getType(List<String> tokens) {
        String firstToken = tokens.get(0);

        if (firstToken.equals("push") || firstToken.equals("pop")) {
            String key = firstToken + " " + tokens.get(1);

            return types.get(key);
        }

        return types.get(firstToken);
    }
}
