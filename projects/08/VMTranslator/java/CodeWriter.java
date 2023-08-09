import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class CodeWriter {
    String fileName="", functionName="";
    HashMap<String, String> map = new HashMap<String, String>();
    FileWriter myWriter;
    CodeWriter(File asmFile) {
        try {
            myWriter = new FileWriter(asmFile.getName());
        } catch(IOException e) {
            System.out.println("Could not create file.");
            e.printStackTrace();
        }
        map.put("local","LCL");
        map.put("argument","ARG");
        map.put("this","THIS");
        map.put("that","THAT");

        map.put("pointer","3");
        map.put("temp","5");

        map.put("add","+");
        map.put("sub","-");
        map.put("neg","-");
        map.put("eq","JEQ");
        map.put("gt","JGT");
        map.put("lt","JLT");
        map.put("and","&");
        map.put("or","|");
        map.put("not","!");
        map.put("id", "0");

        map.put("callIndex", "0");
    }

    void setFileName(String _filename) {
        fileName = _filename;
    }

    void writeArithmetic(String _command) {
        String code="";
        if(_command.equals("add") || _command.equals("sub") || _command.equals("and") || _command.equals("or")) 
            code = "//"+_command+"\n@SP\nM=M-1\nA=M\nD=M\n@SP\nM=M-1\nA=M\nM=M"+map.get(_command)+"D\n@SP\nM=M+1\n";

        else if(_command.equals("neg") || _command.equals("not"))
            code = "//"+_command+"\n@SP\nM=M-1\nA=M\nM="+map.get(_command)+"M\n@SP\nM=M+1\n";

        else if(_command.equals("eq") || _command.equals("gt") || _command.equals("lt")) {
            code = "//"+_command+"\n@SP\nM=M-1\nA=M\nD=M\n@SP\nM=M-1\nA=M\nD=M-D\n@SET_TRUE"+map.get("id")+"\nD;"+map.get(_command)+"\n@SP\nA=M\nM=0\n@END"+map.get("id")+"\n0;JMP\n(SET_TRUE"+map.get("id")+")\n@SP\nA=M\nM=-1\n(END"+map.get("id")+")\n@SP\nM=M+1\n";
            // code = "//"+_command+"\n@SP\nM=M-1\nA=M\nD=M\n@SP\nM=M-1\nA=M\nM=M-D\n@SP\nM=M+1\n";
            map.put("id", Integer.toString(Integer.parseInt(map.get("id")) + 1));
        } else code = "error";
        try{
            myWriter.write(code);
        } catch(IOException e) {
            System.out.println("Could not write to file");
            e.printStackTrace();
        }
    }

    void writePushPop(String _command, String _segment, int _index) {
        String code="";
        if(_command.equals("C_PUSH")) {
            if(_segment.equals("constant")) code = "//push "+_segment+" "+_index+"\n@"+_index+"\nD=A\n@SP\nA=M\nM=D\n@SP\nM=M+1\n";
            else if(_segment.equals("local") || _segment.equals("argument") || _segment.equals("this") || _segment.equals("that"))
                code = "//push "+_segment+" "+_index+"\n@"+_index+"\nD=A\n@"+map.get(_segment)+"\nA=D+M\nD=M\n@SP\nA=M\nM=D\n@SP\nM=M+1\n";
            else if(_segment.equals("static"))
                code = "//push "+_segment+" "+_index+"\n@"+fileName.split("[.]")[0]+"."+_index+"\nD=M\n@SP\nA=M\nM=D\n@SP\nM=M+1\n";
            else if(_segment.equals("temp") || _segment.equals("pointer"))
                code = "//push "+_segment+" "+_index+"\n@"+_index+"\nD=A\n@"+map.get(_segment)+"\nA=D+A\nD=M\n@SP\nA=M\nM=D\n@SP\nM=M+1\n";
        } else if(_command.equals("C_POP")){
            if(_segment.equals("local") || _segment.equals("argument") || _segment.equals("this") || _segment.equals("that"))
                code = "//pop "+_segment+" "+_index+"\n@"+_index+"\nD=A\n@"+map.get(_segment)+"\nD=D+M\n@SP\nM=M-1\nA=M\nD=M+D\nA=D-M\nM=D-A\n";
            else if(_segment.equals("static"))
                code = "//pop "+_segment+" "+_index+"\n@SP\nM=M-1\nA=M\nD=M\n@"+fileName.split("[.]")[0]+"."+_index+"\nM=D\n";
            else if(_segment.equals("temp") || _segment.equals("pointer"))
                code = "//pop "+_segment+" "+_index+"\n@"+_index+"\nD=A\n@"+map.get(_segment)+"\nD=D+A\n@SP\nM=M-1\nA=M\nD=M+D\nA=D-M\nM=D-A\n";
        } else code = "error";
        try{
            myWriter.write(code);
        } catch(IOException e) {
            System.out.println("Could not write to file");
            e.printStackTrace();
        }
    }

    void writeInit() {
        String code = "//Bootstrap code\n@256\nD=A\n@SP\nM=D\n@Sys.init\n0;JMP\n";
        try{
            myWriter.write(code);
        } catch(IOException e) {
            System.out.println("Could not write to file");
            e.printStackTrace();
        }
        // writeCall("Sys.init", 0);
    }

    void writeLabel(String _label) {
        String code = "//label "+_label+"\n("+functionName+"$"+_label+")\n";
        try{
            myWriter.write(code);
        } catch(IOException e) {
            System.out.println("Could not write to file");
            e.printStackTrace();
        }
    }

    void writeGoto(String _label) {
        String code = "//goto "+_label+"\n@"+functionName+"$"+_label+"\n0;JMP\n";
        try{
            myWriter.write(code);
        } catch(IOException e) {
            System.out.println("Could not write to file");
            e.printStackTrace();
        }
    }

    void writeIf(String _label) {
        String code = "//if-goto "+_label+"\n@SP\nM=M-1\n@SP\nA=M\nD=M\n@"+functionName+"$"+_label+"\nD;JNE\n";
        try{
            myWriter.write(code);
        } catch(IOException e) {
            System.out.println("Could not write to file");
            e.printStackTrace();
        }
    }

    void writeFunction(String _functionName, int _numVars) {
        functionName = _functionName;
        String code = "//function "+_functionName+" "+Integer.toString(_numVars)+"\n("+_functionName+")\n"+
            "@"+_numVars+"\nD=A\n(LOOP_LCL_"+map.get("callIndex")+")\n"+
            "@OUT_"+map.get("callIndex")+"\nD;JEQ\n"+
            "@SP\nA=M\nM=0\n@SP\nM=M+1\n"+
            "@LOOP_LCL_"+map.get("callIndex")+"\nD=D-1;JMP\n(OUT_"+map.get("callIndex")+")\n";

        map.put("callIndex", Integer.toString(Integer.parseInt(map.get("callIndex"))+1));
        try{
            myWriter.write(code);
        } catch(IOException e) {
            System.out.println("Could not write to file");
            e.printStackTrace();
        }
    }

    void writeCall(String _functionName, int _numArgs) {
        String code = "//call "+_functionName+" "+Integer.toString(_numArgs)+"\n"+
            "@"+_functionName+"$ret."+map.get("callIndex")+"\nD=A\n@SP\nA=M\nM=D\n@SP\nM=M+1\n"+
            "@LCL\nD=M\n@SP\nA=M\nM=D\n@SP\nM=M+1\n"+
            "@ARG\nD=M\n@SP\nA=M\nM=D\n@SP\nM=M+1\n"+
            "@THIS\nD=M\n@SP\nA=M\nM=D\n@SP\nM=M+1\n"+
            "@THAT\nD=M\n@SP\nA=M\nM=D\n@SP\nM=M+1\n"+
            "@5\nD=A\n@"+Integer.toString(_numArgs)+"\nD=D+A\n@SP\nD=M-D\n@ARG\nM=D\n"+
            "@SP\nD=M\n@LCL\nM=D\n"+
            "@"+_functionName+"\n0;JMP\n"+
            "("+_functionName+"$ret."+map.get("callIndex")+")\n";

        map.put("callIndex", Integer.toString(Integer.parseInt(map.get("callIndex"))+1));
        try{
            myWriter.write(code);
        } catch(IOException e) {
            System.out.println("Could not write to file");
            e.printStackTrace();
        }
    }

    void writeReturn() {
        String code = "//return \n@LCL\nD=M\n@"+functionName+"$endFrame."+map.get("callIndex")+"\nM=D\n"+                                                       //endframe initialize
            "@5\nD=A\n@"+functionName+"$endFrame."+map.get("callIndex")+"\nA=M-D\nD=M\n"+"@"+functionName+"$retAddr."+map.get("callIndex")+"\nM=D\n"+       //set return addr
            "@SP\nM=M-1\nA=M\nD=M\n@ARG\nA=M\nM=D\n"+                                                                                                               //set return value at ARG0
            "@ARG\nD=M\n@SP\nM=D+1\n"+                                                                                                                              //point SP at ARG+1
            "@"+functionName+"$endFrame."+map.get("callIndex")+"\nM=M-1\nA=M\nD=M\n@THAT\nM=D\n"+                                                               //set THAT
            "@"+functionName+"$endFrame."+map.get("callIndex")+"\nM=M-1\nA=M\nD=M\n@THIS\nM=D\n"+                                                               //set THIS  
            "@"+functionName+"$endFrame."+map.get("callIndex")+"\nM=M-1\nA=M\nD=M\n@ARG\nM=D\n"+                                                                //set ARG
            "@"+functionName+"$endFrame."+map.get("callIndex")+"\nM=M-1\nA=M\nD=M\n@LCL\nM=D\n"+                                                                //set LCL
            "@"+functionName+"$retAddr."+map.get("callIndex")+"\nA=M\n0;JMP\n";                                                                                 //goto return addr
        
        map.put("callIndex", Integer.toString(Integer.parseInt(map.get("callIndex"))+1));
        try{
            myWriter.write(code);
        } catch(IOException e) {
            System.out.println("Could not write to file");
            e.printStackTrace();
        }
    }

    void close() {
        try{
            myWriter.close();
        } catch(IOException e) {
            System.out.println("Could not close file.");
            e.printStackTrace();
        }
    }
}
