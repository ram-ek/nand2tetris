//push constant 3030
@3030
D=A
@SP
A=M
M=D
@SP
M=M+1
//pop pointer 0
@0
D=A
@3
D=D+A
@SP
M=M-1
A=M
D=M+D
A=D-M
M=D-A
//push constant 3040
@3040
D=A
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
//push constant 32
@32
D=A
@SP
A=M
M=D
@SP
M=M+1
//pop this 2
@2
D=A
@THIS
D=D+M
@SP
M=M-1
A=M
D=M+D
A=D-M
M=D-A
//push constant 46
@46
D=A
@SP
A=M
M=D
@SP
M=M+1
//pop that 6
@6
D=A
@THAT
D=D+M
@SP
M=M-1
A=M
D=M+D
A=D-M
M=D-A
//push pointer 0
@0
D=A
@3
A=D+A
D=M
@SP
A=M
M=D
@SP
M=M+1
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
//push this 2
@2
D=A
@THIS
A=D+M
D=M
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
//push that 6
@6
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
