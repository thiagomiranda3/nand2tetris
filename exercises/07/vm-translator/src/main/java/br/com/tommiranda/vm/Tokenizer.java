package br.com.tommiranda.vm;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

public class Tokenizer {

    public static List<String> tokenize(String line) {
        line = lineWithoutComment(line)
                .replace("\n", "")
                .replace("\t", "")
                .replace("\r", "")
                .trim();

        if (isInvalidLine(line)) {
            return Collections.emptyList();
        }

        StringTokenizer tokenizer = new StringTokenizer(line, " ");

        List<String> tokens = new ArrayList<>();

        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();
            tokens.add(token);
        }

        return tokens;
    }

    private static String lineWithoutComment(String line) {
        int idx = line.indexOf("/");

        if (idx == 0)
            return "";
        else if (idx > 0)
            return line.substring(0, idx);

        return line;
    }

    private static boolean isInvalidLine(String line) {
        if (line == null || line.isBlank())
            return true;

        if (line.startsWith("//"))
            return true;

        return false;
    }
}
