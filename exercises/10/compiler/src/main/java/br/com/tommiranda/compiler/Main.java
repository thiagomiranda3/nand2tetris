package br.com.tommiranda.compiler;

import br.com.tommiranda.compiler.ast.Node;
import br.com.tommiranda.compiler.errors.SyntaxError;
import br.com.tommiranda.compiler.parsers.structure.ClassParser;
import br.com.tommiranda.compiler.tokenizer.Token;
import br.com.tommiranda.compiler.tokenizer.Tokenizer;
import br.com.tommiranda.compiler.vm.SymbolTable;
import br.com.tommiranda.compiler.vm.VmWriter;
import com.google.gson.Gson;
import org.apache.commons.io.FilenameUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Stream;

public class Main {

    public static void main(String[] args) throws IOException {
        if (args.length == 0) {
            throw new IllegalStateException("No file or directory passed");
        }

        Path path = Paths.get(args[0]);

        if (Files.isDirectory(path)) {
            compileDir(path);
        } else {
            String extension = FilenameUtils.getExtension(path.toString());

            if (!extension.equals("jack")) {
                throw new IllegalStateException("Needs to receive a jack file as param");
            }

            compileFile(path);
        }
    }

    private static void compileDir(Path directory) throws IOException {
        try (Stream<Path> paths = Files.walk(directory, 1)) {
            Iterator<Path> it = paths.iterator();

            while (it.hasNext()) {
                Path path = it.next();

                if (path == directory) {
                    continue;
                }

                if (Files.isDirectory(path)) {
                    compileDir(path);
                } else {
                    String extension = FilenameUtils.getExtension(path.toString());

                    if (!extension.equals("jack")) {
                        continue;
                    }

                    compileFile(path);
                }
            }

            if(!SymbolTable.containsSubroutine("Main", "main")) {
                System.out.println("WARNING: No function void main() found");
            }
        }
    }

    private static void compileFile(Path path) throws IOException {
        String fileName = FilenameUtils.getBaseName(path.toString());

        List<Token> tokens = new Tokenizer().tokenizeFile(path);

        try {
            Node root = new ClassParser().parse(tokens);

            //System.out.println(new Gson().toJson(root));

            List<String> vm_code = new VmWriter(fileName).write(root);

            Files.write(Paths.get(fileName + ".vm"), vm_code);
        } catch (SyntaxError e) {
            System.out.println("In " + fileName + ".jack " + e.getMessage());
        }
    }
}
