package br.com.tommiranda.vm.parsers;

import java.util.List;

public interface Parser {

    String parse(String fileName, List<String> tokens);
}
