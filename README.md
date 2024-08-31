# Overview
- A guided course (https://www.nand2tetris.org/) which takes you through the journey of building a general purpose computer from the very basic NAND gates.
- Task progresses by building structures based on the previously available structures
- Things developed throughout:
  1. Using a custom Hardware Definition Language to build basic gates (AND, OR, NOT, etc) using NAND gates as well using these to make multiplexers and demultiplexers.
  2. Using above structures to make Half Adders, Full Adders, ALU.
  3. Using above structures to make registers, program counters, RAM.
  4. Using custom assembly code to write a multiplier as well as fill command that interfaces with above built hardware to act out fill functionality where it marks RAM cells as filled and same is displayed.
  5. Using above structures to make our own CPU, Memory (RAM and ROM) and connecting them to make our computer.
  6. Making our own assembler that parses assembly code and generates machine code for our computer to understand. This is the start of our own language translator where our language would be built in two parts:
     a. Part 1: Jack -> VMtranslator -> assembly code.
     b. Part 2: Assembly code -> machine code.
  7. Making our VM translator layer that would be used as an intermediate layer between our toy programming language Jack and our assembler language built previously. This language will act as a bridge between our later built programming language and previously built assembler and will be esssential to provide portability as java does.
  8. Here we will be testing our VM Translator by running some programs and checking the overall correctness of our translator. Testing involves:
     a. Static test - Where we will be translating a code which acesses registers, memory and does basic arithmetic.
     b. Pointer test - Here we extend our testing to test the pointer (or reference functionality i.e. this, that) functionality.
     c. Function call test - Here we simulate the function calling aspect and test how our vm translator is handling the function calling and how stack and resgisters are being handled.
     d. Fibonacci test - Here we will run recursive code of getting nth fibonacci number and check how recursive calls are handled by our hardware.
  9. Here we will increase our familiarity with Jack (our own java-like custom language) by writing some basic programs in it.
  10. Here we will build our Tokenizer, Syntax Analyzer and Compile Engine for Jack.
  11. Here we will be doing testing of Jack, and testing its components i.e. Tokenizer, Syntax Analyzer, Compile Engine.
  12. Finally, we will be combining all the components of of computer, making bootstrap process for our computer, making standard interfaces for it i.e. keyboard and screen and making OS for it, which comprises of basic libraries for our programming language Jack.
