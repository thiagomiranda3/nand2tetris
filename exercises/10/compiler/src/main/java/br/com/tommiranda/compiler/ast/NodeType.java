package br.com.tommiranda.compiler.ast;

public enum NodeType {

    // Lexical Elements
    KEYWORD("keyword"),
    SYMBOL("symbol"),
    INTEGER_CONSTANT("integerConstant"),
    STRING_CONSTANT("StringConstant"),
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
    IF_STATEMENT("idStatement"),
    WHILE_STATEMENT("whileStatement"),
    DO_STATEMENT("doStatement"),
    RETURN_STATEMENT("ReturnStatement"),

    // Expressions
    EXPRESSION("expression"),
    TERM("term"),
    SUBROUTINE_CALL("subroutineCall"),
    EXPRESSION_LIST("expressionList"),
    OP("op"),
    UNARY_OP("unaryOp"),
    KEYWORD_CONSTANT("KeywordConstant");

    private String name;

    NodeType(String name) {
        this.name = name;
    }
}
