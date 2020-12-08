push local 7 (LENTO)

// addr = local + 7
@7
D=A
@LCL
A=M+D
D=M
// *SP = *addr
@SP
A=M
M=D
// SP++
@SP
M=M+1

pop local 7 (LENTO)

// addr = local + 7
@7
D=A
@LCL
D=D+M
@R13
M=D

// SP--
@SP
M=M-1
@SP
A=M
D=M

// *addr = *SP
@R13
A=M
M=D

// ---------------------------------------------------------------------------------------------

push local 7 (OTIMIZADO)

// addr = local + 7
@7
D=A
@LCL
A=D+M
D=M
// SP++ e *SP = *addr
@SP
AM=M+1
A=A-1
M=D

pop local 7 (OTIMIZADO)

// addr = local + 7
@7
D=A
@LCL
D=D+M
@R13
M=D

// SP--
@SP
AM=M-1
D=M

// *addr = *SP
@R13
A=M
M=D