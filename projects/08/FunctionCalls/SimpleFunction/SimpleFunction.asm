//function SimpleFunction.test 2
(SimpleFunction.test)
@2
D=A
(LOOP_LCL_0)
@OUT_0
D;JEQ
@SP
A=M
M=0
@SP
M=M+1
@LOOP_LCL_0
D=D-1;JMP
(OUT_0)
//push local 0
@0
D=A
@LCL
A=D+M
D=M
@SP
A=M
M=D
@SP
M=M+1
//push local 1
@1
D=A
@LCL
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
//not
@SP
M=M-1
A=M
M=!M
@SP
M=M+1
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
//return 
@LCL
D=M
@SimpleFunction.test$endFrame.1
M=D
@5
D=A
@SimpleFunction.test$endFrame.1
A=M-D
D=M
@SimpleFunction.test$retAddr.1
M=D
@SP
M=M-1
A=M
D=M
@ARG
A=M
M=D
@ARG
D=M
@SP
M=D+1
@SimpleFunction.test$endFrame.1
M=M-1
A=M
D=M
@THAT
M=D
@SimpleFunction.test$endFrame.1
M=M-1
A=M
D=M
@THIS
M=D
@SimpleFunction.test$endFrame.1
M=M-1
A=M
D=M
@ARG
M=D
@SimpleFunction.test$endFrame.1
M=M-1
A=M
D=M
@LCL
M=D
@SimpleFunction.test$retAddr.1
A=M
0;JMP
