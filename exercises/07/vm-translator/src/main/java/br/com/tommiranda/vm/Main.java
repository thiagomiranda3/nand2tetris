package br.com.tommiranda.vm;

import br.com.tommiranda.vm.errors.TranslationException;
import br.com.tommiranda.vm.parsers.Parser;
import org.apache.commons.io.FilenameUtils;

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

        try (FileWriter writer = new FileWriter(fileName + ".vm")) {
            Iterator<String> it = lines.iterator();
            while (it.hasNext()) {
                String line = it.next();
                List<String> tokens = Tokenizer.tokenize(line);

                if (tokens.isEmpty()) {
                    continue;
                }

                Parser parser = ParserMap.getParser(tokens);

                if (parser == null) {
                    throw new TranslationException("Not recognized tokens: " + line);
                }

                writer.write("// " + line + System.lineSeparator());
                writer.write(parser.parse(fileName, tokens) + System.lineSeparator());
            }
        }
    }
}
