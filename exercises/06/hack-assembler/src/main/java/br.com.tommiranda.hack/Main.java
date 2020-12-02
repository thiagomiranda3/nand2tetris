package br.com.tommiranda.hack;

import br.com.tommiranda.hack.errors.CompileError;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            throw new IllegalStateException("No file found");
        }

        Path path = Paths.get(args[0]);
        String fileName = FilenameUtils.getBaseName(path.toString());
        List<String> lines = Files.readAllLines(path);


        Iterator<String> it = lines.iterator();

        int lineNumber = 0;
        while (it.hasNext()) {
            String line = it.next();

            line = line.replace("\n", "")
                       .replace("\t", "")
                       .replace("\r", "")
                       .replace(" ", "")
                       .trim();

            if (isInvalidLine(line)) {
                it.remove();
                continue;
            }

            line = lineWithoutComment(line);

            if (line == null) continue;

            String label = StringUtils.substringBetween(line, "(", ")");

            if (label != null) {
                if (label.isBlank())
                    throw new CompileError("() without a label inside");

                if (SymbolTable.contains(label))
                    throw new CompileError("Label " + label + " already assigned");

                SymbolTable.add(label, StringUtils.leftPad(Integer.toBinaryString(lineNumber), 16, "0"));
                it.remove();
                continue;
            }

            lineNumber++;
        }

        int memoryPosition = 16;

        try (FileWriter writer = new FileWriter(fileName + ".jack")) {
            for (String line : lines) {
                line = line.replace("\n", "")
                           .replace("\t", "")
                           .replace("\r", "")
                           .replace(" ", "")
                           .trim();

                if (isInvalidLine(line)) continue;

                line = lineWithoutComment(line);

                if (line == null) continue;

                // @123 = 0000000001111011
                if (line.startsWith("@")) {
                    String symbol = line.substring(1);

                    if (NumberUtils.isDigits(symbol)) {
                        writer.write(StringUtils.leftPad(Integer.toBinaryString(Integer.parseInt(symbol)), 16, "0") + System.lineSeparator());
                    } else if (symbol.startsWith("R")) {
                        writer.write(StringUtils.leftPad(Integer.toBinaryString(Integer.parseInt(symbol.substring(1))), 16, "0") + System.lineSeparator());
                    } else if (SymbolTable.contains(symbol)) {
                        writer.write(SymbolTable.get(symbol) + System.lineSeparator());
                    } else {
                        SymbolTable.add(symbol, StringUtils.leftPad(Integer.toBinaryString(memoryPosition++), 16, "0"));
                        writer.write(SymbolTable.get(symbol) + System.lineSeparator());
                    }
                } else {
                    CInstruction c = new CInstruction(line);

                    String machineCode = "111" +
                                         CodeTable.getDest(c.getDest()) +
                                         CodeTable.getComp(c.getComp()) +
                                         CodeTable.getJump(c.getJump());

                    writer.write(machineCode + System.lineSeparator());
                }
            }
        }
    }

    private static String lineWithoutComment(String line) {
        int idx = line.indexOf("/");

        if (idx == 0) {
            return null;
        } else if (idx > 0) {
            line = line.substring(0, idx);
        }

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
