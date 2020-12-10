push local 7

// addr = local + 7
@LCL
D=M
@7
A=D+A
D=M
// SP++ e *SP = *addr
@SP
AM=M+1
A=A-1
M=D

pop local 7

// addr = local + 7
@LCL
D=M
@7
D=D+A
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

add
