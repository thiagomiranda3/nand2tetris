package br.com.tommiranda.compiler.ast;

import br.com.tommiranda.compiler.tokenizer.TokenType;

public enum NodeType {

    // Lexical Elements
    KEYWORD("keyword"),
    SYMBOL("symbol"),
    INTEGER_CONSTANT("integerConstant"),
    STRING_CONSTANT("stringConstant"),
    IDENTIFIER("identifier"),

    // Program Structure
    CLASS("class"),
    CLASS_VAR_DEC("classVarDec"),
    TYPE("type"),
    SUBROUTINE_DEC("subroutineDec"),
    PARAMETER_LIST("parameterList"),
    SUBROUTINE_BODY("subroutineBody"),
    VAR_DEC("varDec"),

    // Statements
    STATEMENTS("statements"),
    LET_STATEMENT("letStatement"),
    IF_STATEMENT("ifStatement"),
    WHILE_STATEMENT("whileStatement"),
    DO_STATEMENT("doStatement"),
    RETURN_STATEMENT("returnStatement"),

    // Expressions
    EXPRESSION("expression"),
    TERM("term"),
    EXPRESSION_LIST("expressionList"),
    OP("op"),
    UNARY_OP("unaryOp"),
    KEYWORD_CONSTANT("KeywordConstant");

    private String name;

    NodeType(String name) {
        this.name = name;
    }

    public static NodeType fromToken(TokenType type) {
        return switch (type) {
            case KEYWORD -> NodeType.KEYWORD;
            case SYMBOL -> NodeType.SYMBOL;
            case INTEGER -> NodeType.INTEGER_CONSTANT;
            case STRING -> NodeType.STRING_CONSTANT;
            case IDENTIFIER -> NodeType.IDENTIFIER;
        };
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
