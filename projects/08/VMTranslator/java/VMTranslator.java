import java.io.File;
import java.util.Scanner;

class VMTranslator {
    static File source, targetASM;
    static Parser parser;
    static CodeWriter code;
    public static void main(String[] args) {
        Scanner myObj = new Scanner(System.in);
        String inputFilename = myObj.nextLine();
        myObj.close();
        source = new File(inputFilename);
        targetASM = new File(inputFilename.split("[.]")[0]+".asm");
        code = new CodeWriter(targetASM);

        if(source.isFile()) {
            processVMFile(source);
        } else if(source.isDirectory()) {
            code.writeInit();
            processDirectory(source);
        }

        code.close();
    }

    static void processVMFile(File vmFile){
        parser = new Parser(vmFile);
        while(parser.hasMoreCommands()) {
                code.setFileName(vmFile.getName().split("[.]")[0]);
                parser.advance();
                if(parser.commandType() == "C_ARITHMETIC") {
                    code.writeArithmetic(parser.arg1());
                } else if(parser.commandType() == "C_PUSH" || parser.commandType() == "C_POP") {
                    code.writePushPop(parser.commandType(), parser.arg1(), parser.arg2());
                } else if(parser.commandType() == "C_LABEL") {
                    code.writeLabel(parser.arg1());
                } else if(parser.commandType() == "C_GOTO") {
                    code.writeGoto(parser.arg1());
                } else if(parser.commandType() == "C_IF") {
                    code.writeIf(parser.arg1());
                } else if(parser.commandType() == "C_CALL") {
                    code.writeCall(parser.arg1(), parser.arg2());
                } else if(parser.commandType() == "C_FUNCTION") {
                    code.writeFunction(parser.arg1(), parser.arg2());
                } else if(parser.commandType() == "C_RETURN") {
                    code.writeReturn();
                }
            }
    }
    
    static void processDirectory(File dir) {
        File[] files = dir.listFiles();

        for(File file : files) {
            if(file.isDirectory()) processDirectory(file);
            else if(file.getName().split("[.]")[1].equals("vm")) processVMFile(file);
        }
    }
}