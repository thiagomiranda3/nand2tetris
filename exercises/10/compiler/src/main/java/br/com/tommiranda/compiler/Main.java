package br.com.tommiranda.compiler;

import br.com.tommiranda.compiler.ast.Node;
import br.com.tommiranda.compiler.errors.SyntaxError;
import br.com.tommiranda.compiler.parsers.structure.ClassParser;
import br.com.tommiranda.compiler.tokenizer.Token;
import br.com.tommiranda.compiler.tokenizer.Tokenizer;
import br.com.tommiranda.compiler.vm.Subroutine;
import br.com.tommiranda.compiler.vm.SubroutineKind;
import br.com.tommiranda.compiler.vm.SymbolTable;
import br.com.tommiranda.compiler.vm.VmWriter;
import org.apache.commons.io.FilenameUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Stream;

public class Main {

    private static boolean compilationOK = true;
    private static final Set<String> fileNames = new HashSet<>();

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

        verifySubroutines();

        if(!compilationOK) {
            for (String fileName : fileNames) {
                Files.deleteIfExists(Paths.get(fileName + ".vm"));
            }
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
                compilationOK = false;
            }
        }
    }

    private static void compileFile(Path path) throws IOException {
        String fileName = FilenameUtils.getBaseName(path.toString());

        fileNames.add(fileName);

        List<Token> tokens = new Tokenizer().tokenizeFile(path);

        try {
            Node root = new ClassParser().parse(tokens);

            List<String> vm_code = new VmWriter(fileName).write(root);

            Files.write(Paths.get(fileName + ".vm"), vm_code);
        } catch (SyntaxError e) {
            System.out.println("In " + fileName + ".jack " + e.getMessage());
            compilationOK = false;
        }
    }

    private static void verifySubroutines() {
        Set<String> whitelistClasses = Set.of("Screen", "Memory", "Sys", "Keyboard", "Output", "Math");

        Map<String, Subroutine> subroutineTable = SymbolTable.getSubroutineTable();

        for (Subroutine subroutineCall : SymbolTable.getSubroutinesToVerify()) {
            if(whitelistClasses.contains(subroutineCall.getClassName())) {
                continue;
            }

            Subroutine declaredSubroutine = subroutineTable.get(subroutineCall.getClassName() + "." + subroutineCall.getName());

            if(declaredSubroutine == null) {
                showError(subroutineCall.getCallClass(), subroutineCall.getCallNumber(), "Subroutine " + subroutineCall.getClassName() + "." + subroutineCall.getName() + " doesn't exist");
                compilationOK = false;
                continue;
            }

            if(declaredSubroutine.getKind().equals(SubroutineKind.CONSTRUCTOR) && subroutineCall.getKind().equals(SubroutineKind.FUNCTION)) {
                continue;
            }

            if(!declaredSubroutine.getKind().equals(subroutineCall.getKind())) {
                showError(subroutineCall.getCallClass(), subroutineCall.getCallNumber(), declaredSubroutine.getKind().getName() + " " + subroutineCall.getClassName() + "." + subroutineCall.getName() + " called as a " + subroutineCall.getKind().getName());
                compilationOK = false;
            }
        }
    }

    private static void showError(String fileName, int lineNumber, String message) {
        System.out.println("In " + fileName + ".jack (line " + lineNumber + "): " + message);
    }
}
