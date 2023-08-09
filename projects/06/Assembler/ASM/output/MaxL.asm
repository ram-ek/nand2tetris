// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/06/max/Max.asm

// Computes R2 = max(R0, R1)  (R0,R1,R2 refer to RAM[0],RAM[1],RAM[2])

@0
   D=M              // D = first number
@1
   D=D-M            // D = first number - second number
@19
   D;JGT            // if D>0 (first is greater) goto output_first
@1
   D=M              // D = second number
@20
   0;JMP            // goto output_d
(OUTPUT_FIRST)
@0
   D=M              // D = first number
(OUTPUT_D)
@2
   M=D              // M[2] = D (greatest number)
(INFINITE_LOOP)
@21
   0;JMP            // infinite loop
