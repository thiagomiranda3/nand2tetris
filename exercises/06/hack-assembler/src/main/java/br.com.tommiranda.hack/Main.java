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
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            throw new IllegalStateException("No file found");
        }

        Path path = Paths.get(args[0]);
        String fileName = FilenameUtils.getBaseName(path.toString());
        List<String> lines = Files.readAllLines(path);

        int lineNumber = 0;
        for (String line : lines) {
            line = line.replace("\n", "")
                       .replace("\t", "")
                       .replace("\r", "")
                       .replace(" ", "")
                       .trim();

            if (isInvalidLine(line)) continue;

            String label = StringUtils.substringBetween(line, "(", ")");

            if (label != null) {
                if (label.isBlank())
                    throw new CompileError("() without a label inside");

                if (SymbolTable.contains(label))
                    throw new CompileError("Label " + label + " already assigned");

                SymbolTable.add(label, StringUtils.leftPad(Integer.toBinaryString(lineNumber), 16, "0"));
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

                // @123 = 0000000001111011
                if (line.startsWith("@")) {
                    String symbol = line.substring(1);

                    if (NumberUtils.isDigits(symbol)) {
                        writer.write(StringUtils.leftPad(Integer.toBinaryString(Integer.parseInt(symbol)), 16, "0") + System.lineSeparator());
                    } else if (symbol.startsWith("R")) {
                        writer.write(Integer.toBinaryString(Integer.parseInt(symbol.substring(1))) + System.lineSeparator());
                    } else if (SymbolTable.contains(symbol)) {
                        writer.write(SymbolTable.get(symbol) + System.lineSeparator());
                    } else {
                        SymbolTable.add(symbol, StringUtils.leftPad(Integer.toBinaryString(memoryPosition++), 16, "0"));
                        writer.write(SymbolTable.get(symbol) + System.lineSeparator());
                    }
                }
            }
        }
    }

    private static boolean isInvalidLine(String line) {
        if (line == null || line.isBlank())
            return true;

        if (line.startsWith("//"))
            return true;

        return false;
    }
}
