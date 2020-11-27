// Multiplies R0 and R1 and stores the result in R2.
// (R0, R1, R2 refer to RAM[0], RAM[1], and RAM[2], respectively.)

@i
M=0
@R2
M=0

(LOOP)
    @i
    D=M
    @R1
    D=D-M
    @END
    D;JGE   // if i >= R1 goto END

    @R0
    D=M
    @R2
    M=M+D   // R2 = R2 + R0

    @i
    M=M+1   // i = i + 1

    @LOOP
    0;JMP

(END)
    @END
    0;JMP