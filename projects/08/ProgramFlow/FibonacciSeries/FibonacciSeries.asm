//push argument 1
@1
D=A
@ARG
A=D+M
D=M
@SP
A=M
M=D
@SP
M=M+1
//pop pointer 1
@1
D=A
@3
D=D+A
@SP
M=M-1
A=M
D=M+D
A=D-M
M=D-A
//push constant 0
@0
D=A
@SP
A=M
M=D
@SP
M=M+1
//pop that 0
@0
D=A
@THAT
D=D+M
@SP
M=M-1
A=M
D=M+D
A=D-M
M=D-A
//push constant 1
@1
D=A
@SP
A=M
M=D
@SP
M=M+1
//pop that 1
@1
D=A
@THAT
D=D+M
@SP
M=M-1
A=M
D=M+D
A=D-M
M=D-A
//push argument 0
@0
D=A
@ARG
A=D+M
D=M
@SP
A=M
M=D
@SP
M=M+1
//push constant 2
@2
D=A
@SP
A=M
M=D
@SP
M=M+1
//sub
@SP
M=M-1
A=M
D=M
@SP
M=M-1
A=M
M=M-D
@SP
M=M+1
//pop argument 0
@0
D=A
@ARG
D=D+M
@SP
M=M-1
A=M
D=M+D
A=D-M
M=D-A
//label MAIN_LOOP_START
(FibonacciSeries.$MAIN_LOOP_START)
//push argument 0
@0
D=A
@ARG
A=D+M
D=M
@SP
A=M
M=D
@SP
M=M+1
//if-goto COMPUTE_ELEMENT
@SP
M=M-1
@SP
A=M
D=M
@FibonacciSeries.$COMPUTE_ELEMENT
D;JNE
//goto END_PROGRAM
@FibonacciSeries.$END_PROGRAM
0;JMP
//label COMPUTE_ELEMENT
(FibonacciSeries.$COMPUTE_ELEMENT)
//push that 0
@0
D=A
@THAT
A=D+M
D=M
@SP
A=M
M=D
@SP
M=M+1
//push that 1
@1
D=A
@THAT
A=D+M
D=M
@SP
A=M
M=D
@SP
M=M+1
//add
@SP
M=M-1
A=M
D=M
@SP
M=M-1
A=M
M=M+D
@SP
M=M+1
//pop that 2
@2
D=A
@THAT
D=D+M
@SP
M=M-1
A=M
D=M+D
A=D-M
M=D-A
//push pointer 1
@1
D=A
@3
A=D+A
D=M
@SP
A=M
M=D
@SP
M=M+1
//push constant 1
@1
D=A
@SP
A=M
M=D
@SP
M=M+1
//add
@SP
M=M-1
A=M
D=M
@SP
M=M-1
A=M
M=M+D
@SP
M=M+1
//pop pointer 1
@1
D=A
@3
D=D+A
@SP
M=M-1
A=M
D=M+D
A=D-M
M=D-A
//push argument 0
@0
D=A
@ARG
A=D+M
D=M
@SP
A=M
M=D
@SP
M=M+1
//push constant 1
@1
D=A
@SP
A=M
M=D
@SP
M=M+1
//sub
@SP
M=M-1
A=M
D=M
@SP
M=M-1
A=M
M=M-D
@SP
M=M+1
//pop argument 0
@0
D=A
@ARG
D=D+M
@SP
M=M-1
A=M
D=M+D
A=D-M
M=D-A
//goto MAIN_LOOP_START
@FibonacciSeries.$MAIN_LOOP_START
0;JMP
//label END_PROGRAM
(FibonacciSeries.$END_PROGRAM)
