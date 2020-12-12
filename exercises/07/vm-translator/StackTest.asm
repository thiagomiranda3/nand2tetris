// push constant 17
@17
D=A
@SP
AM=M+1
A=A-1
M=D
// push constant 17
@17
D=A
@SP
AM=M+1
A=A-1
M=D
// eq
@SP
AM=M-1
D=M
A=A-1
D=M-D;
@EQ_2
D;JEQ
@SP
A=M-1
M=1
@END_2
0;JMP
(EQ_2)
@SP
A=M-1
M=1
(END_2)
// push constant 17
@17
D=A
@SP
AM=M+1
A=A-1
M=D
// push constant 16
@16
D=A
@SP
AM=M+1
A=A-1
M=D
// eq
@SP
AM=M-1
D=M
A=A-1
D=M-D;
@EQ_5
D;JEQ
@SP
A=M-1
M=1
@END_5
0;JMP
(EQ_5)
@SP
A=M-1
M=1
(END_5)
// push constant 16
@16
D=A
@SP
AM=M+1
A=A-1
M=D
// push constant 17
@17
D=A
@SP
AM=M+1
A=A-1
M=D
// eq
@SP
AM=M-1
D=M
A=A-1
D=M-D;
@EQ_8
D;JEQ
@SP
A=M-1
M=1
@END_8
0;JMP
(EQ_8)
@SP
A=M-1
M=1
(END_8)
// push constant 892
@892
D=A
@SP
AM=M+1
A=A-1
M=D
// push constant 891
@891
D=A
@SP
AM=M+1
A=A-1
M=D
// lt
@SP
AM=M-1
D=M
A=A-1
D=M-D;
@EQ_11
D;JLT
@SP
A=M-1
M=0
@END_11
0;JMP
(EQ_11)
@SP
A=M-1
M=-1
(END_11)
// push constant 891
@891
D=A
@SP
AM=M+1
A=A-1
M=D
// push constant 892
@892
D=A
@SP
AM=M+1
A=A-1
M=D
// lt
@SP
AM=M-1
D=M
A=A-1
D=M-D;
@EQ_14
D;JLT
@SP
A=M-1
M=0
@END_14
0;JMP
(EQ_14)
@SP
A=M-1
M=-1
(END_14)
// push constant 891
@891
D=A
@SP
AM=M+1
A=A-1
M=D
// push constant 891
@891
D=A
@SP
AM=M+1
A=A-1
M=D
// lt
@SP
AM=M-1
D=M
A=A-1
D=M-D;
@EQ_17
D;JLT
@SP
A=M-1
M=0
@END_17
0;JMP
(EQ_17)
@SP
A=M-1
M=-1
(END_17)
// push constant 32767
@32767
D=A
@SP
AM=M+1
A=A-1
M=D
// push constant 32766
@32766
D=A
@SP
AM=M+1
A=A-1
M=D
// gt
@SP
AM=M-1
D=M
A=A-1
D=M-D;
@EQ_20
D;JGT
@SP
A=M-1
M=0
@END_20
0;JMP
(EQ_20)
@SP
A=M-1
M=-1
(END_20)
// push constant 32766
@32766
D=A
@SP
AM=M+1
A=A-1
M=D
// push constant 32767
@32767
D=A
@SP
AM=M+1
A=A-1
M=D
// gt
@SP
AM=M-1
D=M
A=A-1
D=M-D;
@EQ_23
D;JGT
@SP
A=M-1
M=0
@END_23
0;JMP
(EQ_23)
@SP
A=M-1
M=-1
(END_23)
// push constant 32766
@32766
D=A
@SP
AM=M+1
A=A-1
M=D
// push constant 32766
@32766
D=A
@SP
AM=M+1
A=A-1
M=D
// gt
@SP
AM=M-1
D=M
A=A-1
D=M-D;
@EQ_26
D;JGT
@SP
A=M-1
M=0
@END_26
0;JMP
(EQ_26)
@SP
A=M-1
M=-1
(END_26)
// push constant 57
@57
D=A
@SP
AM=M+1
A=A-1
M=D
// push constant 31
@31
D=A
@SP
AM=M+1
A=A-1
M=D
// push constant 53
@53
D=A
@SP
AM=M+1
A=A-1
M=D
// add
@SP
AM=M-1
D=M
A=A-1
M=D+M
// push constant 112
@112
D=A
@SP
AM=M+1
A=A-1
M=D
// sub
@SP
AM=M-1
D=M
A=A-1
M=M-D
// neg
@SP
A=M-1
M=-M
// and
@SP
AM=M-1
D=M
A=A-1
M=D&M
// push constant 82
@82
D=A
@SP
AM=M+1
A=A-1
M=D
// or
@SP
AM=M-1
D=M
A=A-1
M=D|M
// not
@SP
A=M-1
M=!M
