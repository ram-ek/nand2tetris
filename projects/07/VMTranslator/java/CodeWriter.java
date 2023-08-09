import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;

public class CodeWriter {
    String fileName="";
    HashMap<String, String> map = new HashMap<String, String>();
    FileWriter myWriter;
    CodeWriter(File asmFile) {
        try {
            fileName = asmFile.getName();
            myWriter = new FileWriter(fileName);
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

    void writeArithmetic(String _command) {
        String code="";
        if(_command.equals("add") || _command.equals("sub") || _command.equals("and") || _command.equals("or")) 
            code = "//"+_command+"\n@SP\nM=M-1\nA=M\nD=M\n@SP\nM=M-1\nA=M\nM=M"+map.get(_command)+"D\n@SP\nM=M+1\n";

        else if(_command.equals("neg") || _command.equals("not"))
            code = "//"+_command+"\n@SP\nM=M-1\nA=M\nM="+map.get(_command)+"M\n@SP\nM=M+1\n";

        else if(_command.equals("eq") || _command.equals("gt") || _command.equals("lt")) {
            code = "//"+_command+"\n@SP\nM=M-1\nA=M\nD=M\n@SP\nM=M-1\nA=M\nD=M-D\n@SET_TRUE"+map.get("id")+"\nD;"+map.get(_command)+"\n@SP\nA=M\nM=0\n@END"+map.get("id")+"\n0;JMP\n(SET_TRUE"+map.get("id")+")\n@SP\nA=M\nM=-1\n(END"+map.get("id")+")\n@SP\nM=M+1\n";
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

    void close() {
        try{
            myWriter.close();
        } catch(IOException e) {
            System.out.println("Could not close file.");
            e.printStackTrace();
        }
    }
}
