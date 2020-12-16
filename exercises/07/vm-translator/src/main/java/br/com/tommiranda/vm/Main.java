package br.com.tommiranda.vm;

import br.com.tommiranda.vm.errors.TranslationException;
import br.com.tommiranda.vm.parsers.CallParser;
import br.com.tommiranda.vm.parsers.FunctionParser;
import br.com.tommiranda.vm.parsers.Parser;
import org.apache.commons.io.FilenameUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) throws IOException {
        if (args.length == 0)
            throw new IllegalStateException("No file found");

        Path path = Paths.get(args[0]);

        List<String> assembly;
        if (Files.isDirectory(path)) {
            assembly = translateVm(path, bootstrap());
        } else {
            assembly = translateFile(path);
        }

        String fileName = FilenameUtils.getBaseName(path.toString());

        Files.write(Paths.get(fileName + ".asm"), assembly);
    }

    private static List<String> bootstrap() {
        List<String> assembly = new ArrayList<>();

        assembly.add("@256");
        assembly.add("D=A");
        assembly.add("@SP");
        assembly.add("M=D");
        assembly.add(new CallParser().parse(null, Arrays.asList("call", "Sys.init", "0"), 0, null));

        return assembly;
    }

    private static List<String> translateVm(Path directory, List<String> assembly) throws IOException {
        try (Stream<Path> paths = Files.walk(directory, 1)) {
            Iterator<Path> it = paths.iterator();

            while (it.hasNext()) {
                Path path = it.next();

                if (path == directory) continue;

                if (Files.isDirectory(path)) {
                    translateVm(path, assembly);
                } else {
                    String extension = FilenameUtils.getExtension(path.toString());

                    if (!extension.equals("vm"))
                        continue;

                    assembly.addAll(translateFile(path));
                }
            }
        }

        return assembly;
    }

    private static List<String> translateFile(Path path) throws IOException {
        String fileName = FilenameUtils.getBaseName(path.toString());
        List<String> lines = Files.readAllLines(path);

        int lineNumber = 0;
        List<String> assembly = new ArrayList<>();

        String functionName = "";

        for (String line : lines) {
            List<String> tokens = Tokenizer.tokenize(line);

            if (tokens.isEmpty()) continue;

            Parser parser = ParserMap.getParser(tokens);

            if (parser == null)
                throw new TranslationException("Not recognized tokens: " + line);

            if (parser instanceof FunctionParser)
                functionName = tokens.get(1);

            assembly.add("// " + line);
            assembly.add(parser.parse(fileName, tokens, lineNumber, functionName));

            lineNumber++;
        }

        return assembly;
    }
}
