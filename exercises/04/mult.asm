// Multiplies R0 and R1 and stores the result in R2.
// (R0, R1, R2 refer to RAM[0], RAM[1], and RAM[2], respectively.)

// Defining R2 to 0
@R2
M=0

@R0
D=M
@R1
D=D-M
@SET_OPERAND
D;JGE           // if R0 >= R1 goto SET_OPERAND

@R0
D=M
@i
M=D
@R1
D=M
@operand        // R1 is the biggest number
M=D
@LOOP
0;JMP

(SET_OPERAND)
    @R1
    D=M
    @i
    M=D
    @R0
    D=M
    @operand    // R0 is the biggest number
    M=D

(LOOP)
    @i
    D=M
    @END
    D;JEQ   // if i >= R1 goto END

    @operand
    D=M
    @R2
    M=M+D   // R2 = R2 + operand

    @i
    M=M-1   // i = i - 1

    @LOOP
    0;JMP

(END)
    @END
    0;JMP