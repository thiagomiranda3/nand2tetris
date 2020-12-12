// true = -1 e false = 0

// eq
@SP
AM=M-1
D=M
A=A-1
D=M-D;
@EQ_lineNumber
D;JEQ
@SP
A=M-1
M=0
@END_lineNumber
0;JMP
(EQ_lineNumber)
@SP
A=M-1
M=-1
(END_lineNumber)

// gt
@SP
AM=M-1
D=M
A=A-1
D=M-D;
@EQ_lineNumber
D;JGT
@SP
A=M-1
M=0
@END_lineNumber
0;JMP
(EQ_lineNumber)
@SP
A=M-1
M=-1
(END_lineNumber)

// lt
@SP
AM=M-1
D=M
A=A-1
D=M-D;
@EQ_lineNumber
D;JLT
@SP
A=M-1
M=0
@END_lineNumber
0;JMP
(EQ_lineNumber)
@SP
A=M-1
M=-1
(END_lineNumber)


// push temp 0
@R5
D=M
@SP
AM=M+1
A=A-1
M=D

// pop temp 0
@SP
AM=M-1
D=M
@R5
M=D

// push temp 6
@5
D=A
@6
D=D+A
A=D
D=M
@SP
AM=M+1
A=A-1
M=D

// pop temp 6
@5
D=A
@6
D=D+A
@R13
M=D
@SP
AM=M-1
D=M
@R13
A=M
M=D

// push pointer 0
@THIS
D=M
@SP
AM=M+1
A=A-1
M=D

// pop pointer 1
@SP
AM=M-1
D=M
@THAT
M=D

// push constant 7
@7
D=A
@SP
AM=M+1
A=A-1
M=D

// push static 7
@fileName.7
D=M
@SP
AM=M+1
A=A-1
M=D

// pop static 7
@SP
AM=M-1
D=M
@fileName.7
M=D

// push local 7
@LCL
D=M
@7
A=D+A
D=M
@SP
AM=M+1
A=A-1
M=D

// pop local 7
@LCL
D=M
@7
D=D+A
@R13
M=D
@SP
AM=M-1
D=M
@R13
A=M
M=D

// add
@SP
AM=M-1
D=M
A=A-1
M=D+M

// sub
@SP
AM=M-1
D=M
A=A-1
M=M-D

// neg
@SP
A=M-1
M=!M

// and
@SP
AM=M-1
D=M
A=A-1
M=D&M