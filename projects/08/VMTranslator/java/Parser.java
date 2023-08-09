import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Parser {
    private Scanner myReader;
    String line, commandType, arg1;
    int arg2;

    Parser(File vmFile) {
        try {
            myReader = new Scanner(vmFile);

        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    boolean hasMoreCommands() { return myReader.hasNextLine(); }

    void advance() {
        line = myReader.nextLine();
        String[] splitLine = line.split("//");
        if(splitLine.length == 0) { commandType = "C_COMMENT"; return; }
        String vmInst = splitLine[0].trim();
        String[] components = vmInst.split(" ");
        if(components[0].equals("")) { commandType = "C_COMMENT"; }
        else if(components[0].equals("push")) {
            commandType = "C_PUSH";
            arg1 = components[1];
            arg2 = Integer.parseInt(components[2]);
        }
        else if(components[0].equals("pop")) {
            commandType = "C_POP";
            arg1 = components[1];
            arg2 = Integer.parseInt(components[2]);
        }
        else if(components[0].equals("add") || components[0].equals("sub") ||components[0].equals("neg") || 
            components[0].equals("eq") || components[0].equals("gt") || components[0].equals("lt") || 
            components[0].equals("and") || components[0].equals("or") || components[0].equals("not"))
        {
            commandType = "C_ARITHMETIC";
            arg1 = components[0];
            arg2 = -1;
        } else if(components[0].equals("label")) {
            commandType = "C_LABEL";
            arg1 = components[1];
            arg2 = -1;
        } else if(components[0].equals("goto")) {
            commandType = "C_GOTO";
            arg1 = components[1];
            arg2 = -1;
        } else if(components[0].equals("if-goto")) {
            commandType = "C_IF";
            arg1 = components[1];
            arg2 = -1;
        } else if(components[0].equals("function")) {
            commandType = "C_FUNCTION";
            arg1 = components[1];
            arg2 = Integer.parseInt(components[2]);
        } else if(components[0].equals("call")) {
            commandType = "C_CALL";
            arg1 = components[1];
            arg2 = Integer.parseInt(components[2]);
        } else if(components[0].equals("return")) {
            commandType = "C_RETURN";
            arg1 = "";
            arg2 = -1;
        }
    }

    String commandType() { return commandType; }

    String arg1() { return arg1; }

    int arg2() { return arg2; }
}
