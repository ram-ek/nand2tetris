// This file is part of www.nand2tetris.org
// and the book "The Elements of Computing Systems"
// by Nisan and Schocken, MIT Press.
// File name: projects/04/Fill.asm

// Runs an infinite loop that listens to the keyboard input.
// When a key is pressed (any key), the program blackens the screen,
// i.e. writes "black" in every pixel;
// the screen should remain fully black as long as the key is pressed. 
// When no key is pressed, the program clears the screen, i.e. writes
// "white" in every pixel;
// the screen should remain fully clear as long as no key is pressed.

// Put your code here.
   
   (LOOP)
   @KBD
   D = M

   @BLACK
   D;JNE
   
   @WHITE
   D;JEQ
   
   @LOOP
   0;JMP

   
   (WHITE)
   @SCREEN
   D = A
   @addr
   M = D	// @addr = @SCREEN
   @i
   M = 0	// i = 0
   
   (MAKEWHITE)
   @addr
   D = M
   @i
   A = D + M	// A = @addr + @i

   // if addr + i >= kbd
   D = A
   @KBD	
   D = D - A	// D = @addr + @i - @KEYBOARD

   @LOOP
   D;JGE
   
   @addr
   D = M
   @i
   A = D + M	// A = @addr + @i
   M = 0	// Screen black
   
   @i
   M = M + 1   	
   
   @MAKEWHITE
   D;JNE	// Make next memory map black

   @LOOP
   0;JMP	// Screen black, go to check for next event


   (BLACK)
   @SCREEN
   D = A
   @addr
   M = D	// @addr = @SCREEN
   @i
   M = 0	// i = 0
   
   (MAKEBLACK)
   @addr
   D = M
   @i
   A = D + M	// A = @addr + @i

   // if addr + i >= kbd
   D = A
   @KBD	
   D = D - A	// D = @addr + @i - @KEYBOARD

   @LOOP
   D;JGE
   
   @addr
   D = M
   @i
   A = D + M	// A = @addr + @i
   M = -1	// Screen black
   
   @i
   M = M + 1  

   @MAKEBLACK
   D;JNE	// Make next memory map black
 	
   
   @LOOP
   0;JMP	// Screen black, go to check for next event
