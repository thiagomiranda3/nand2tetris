package br.com.tommiranda.hack;

import br.com.tommiranda.hack.errors.CompileError;
import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class Main {

    public static void main(String[] args) throws IOException {
        if(args.length == 0) {
            throw new IllegalStateException("No file found");
        }

        Path path = Paths.get(args[0]);
        List<String> lines = Files.readAllLines(path);

        for (String line : lines) {
            String label = StringUtils.substringBetween(line, "(", ")");

            if(label != null && label.isBlank()) {
                throw new CompileError("() without a label inside");
            }


        }
    }
}
