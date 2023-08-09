import java.io.File;

class VMTranslator {
    public static void main(String[] args) {
        String inputFilename="StaticTest.vm";
        File vmFile = new File(inputFilename);
        File asmFile = new File(inputFilename.split("[.]")[0]+".asm");
        Parser parser = new Parser(vmFile);
        CodeWriter code = new CodeWriter(asmFile);

        while(parser.hasMoreCommands()) {
            parser.advance();
            if(parser.commandType() == "C_ARITHMETIC") {
                code.writeArithmetic(parser.arg1());
            } else if(parser.commandType() == "C_PUSH" || parser.commandType() == "C_POP") {
                code.writePushPop(parser.commandType(), parser.arg1(), parser.arg2());
            }
        }

        code.close();
    }
}