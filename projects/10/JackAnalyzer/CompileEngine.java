import java.io.FileWriter;

public class CompileEngine {
    JackTokenizer _tokenizer;
    FileWriter _myWriter;

    CompileEngine(JackTokenizer tokenizer, String outputFilename) {
        _tokenizer = tokenizer;
        try {
            _myWriter = new FileWriter(outputFilename);
            if(_tokenizer.hasMoreTokens()) {
                _tokenizer.advance();
                CompileClass();
            }
            _myWriter.close();
        } catch(Exception e) {
            System.out.println("Could not create file.");
            e.printStackTrace();
        }
    }

    private void _eat(String token) {
        if(!_tokenizer.currToken().equals(token)) {
            //error handling -> throw error
            System.out.println("mismatch: expected " + token + " got " +_tokenizer.currToken());
            System.exit(0);
        } else {
            if(_tokenizer.tokenType().equals("KEYWORD")) CompileKeyword();
            else if(_tokenizer.tokenType().equals("SYMBOL")) CompileSymbol();
            else if(_tokenizer.tokenType().equals("INT_CONST")) CompileIntegerConstant();
            else if(_tokenizer.tokenType().equals("STRING_CONST")) CompileStringConstant();
            else if(_tokenizer.tokenType().equals("IDENTIFIER")) CompileIdentifier();
        }
    }

    void CompileClass() {
        try{
            //start tag
            _myWriter.write("<class>\n");

            _eat("class");
            String className = _tokenizer.currToken();
            _eat(className);
            _eat("{");

            while(_tokenizer.currToken().equals("static") || _tokenizer.currToken().equals("field"))
                CompileClassVarDec();
            
            while(_tokenizer.currToken().equals("constructor") || _tokenizer.currToken().equals("function") || _tokenizer.currToken().equals("method"))
                CompileSubroutineDec();

            _eat("}");

            //end tag
            _myWriter.write("</class>\n");
        } catch(Exception e) {
            System.out.println("Could not create file.");
            e.printStackTrace();
        }
    }

    void CompileClassVarDec() {
        try {
            //start tag
            _myWriter.write("<classVarDec>\n");

            //static or field
            _eat(_tokenizer.currToken());
            
            //type
            _eat(_tokenizer.currToken());

            //varName
            _eat(_tokenizer.currToken());

            while(_tokenizer.currToken().equals(",")) {
                _eat(",");
                _eat(_tokenizer.currToken());
            }

            _eat(";");

            //end tag
            _myWriter.write("</classVarDec>\n");
        } catch(Exception e) {
            System.out.println("Could not create file.");
            e.printStackTrace();
        }
    }

    void CompileDoStatement() {
        try{
            //start tag
            _myWriter.write("<doStatement>\n");
            
            _eat("do");

            //subroutine name or class name or var name
            _eat(_tokenizer.currToken());

            if(_tokenizer.currToken().equals(".")) {
                // .
                _eat(_tokenizer.currToken());

                // subroutine name
                _eat(_tokenizer.currToken());
            }
               
            _eat("(");

            CompileExpressionList();

            _eat(")");

            _eat(";");

            //end tag
            _myWriter.write("</doStatement>\n");
        } catch(Exception e) {
            System.out.println("Could not create file.");
            e.printStackTrace();
        }
    }

    void CompileExpression() {
        try {
            //start tag
            _myWriter.write("<expression>\n");

            CompileTerm();

            while(_tokenizer.currToken().equals("+") || _tokenizer.currToken().equals("-") || _tokenizer.currToken().equals("*") || _tokenizer.currToken().equals("/") ||
                _tokenizer.currToken().equals("&") || _tokenizer.currToken().equals("|") || _tokenizer.currToken().equals("<") || _tokenizer.currToken().equals(">") ||
                _tokenizer.currToken().equals("=")) {
                    _eat(_tokenizer.currToken());

                    CompileTerm();
            }


            //end tag
            _myWriter.write("</expression>\n");
        } catch(Exception e) {
            System.out.println("Could not write to file");
            e.printStackTrace();
        }
    }

    void CompileExpressionList() {
        try {
            //start tag
            _myWriter.write("<expressionList>\n");

            while(!_tokenizer.currToken().equals(")")) {

                CompileExpression();

                if(_tokenizer.currToken().equals(","))
                    _eat(",");

            }

            //end tag
            _myWriter.write("</expressionList>\n");
        } catch(Exception e) {
            System.out.println("Could not write to file");
            e.printStackTrace();
        }
    }

    void CompileIdentifier() {
        try {
            _myWriter.write("<identifier> "+_tokenizer.identifier()+" </identifier>\n");
            _tokenizer.advance();
        } catch(Exception e) {
            System.out.println("Could not write to file");
            e.printStackTrace();
        }
    }

    void CompileIfStatement() {
        try{
            //start tag
            _myWriter.write("<ifStatement>\n");

            _eat("if");
            _eat("(");

            CompileExpression();
            
            _eat(")");
            _eat("{");
            
            CompileStatements();
            
            _eat("}");
            
            if(_tokenizer.currToken().equals("else")) {
                _eat("else");
                _eat("{");

                CompileStatements();
                
                _eat("}");
            }

            //end tag
            _myWriter.write("</ifStatement>\n");
        } catch(Exception e) {
            System.out.println("Could not write to file");
            e.printStackTrace();
        }
    }

    void CompileIntegerConstant() {
        try {
            _myWriter.write("<integerConstant> "+_tokenizer.intVal()+" </integerConstant>\n");
            _tokenizer.advance();
        } catch(Exception e) {
            System.out.println("Could not write to file");
            e.printStackTrace();
        }
    }

    void CompileKeyword() {
        try {
            _myWriter.write("<keyword> "+_tokenizer.currToken()+" </keyword>\n");
            _tokenizer.advance();
        } catch(Exception e) {
            System.out.println("Could not write to file");
            e.printStackTrace();
        }
    }

    void CompileLetStatement() {
        try {
            //start tag
            _myWriter.write("<letStatement>\n");

            _eat("let");

            _eat(_tokenizer.currToken());
            
            if(_tokenizer.currToken().equals("[")) {
                _eat("[");

                CompileExpression();
                
                _eat("]");
            }

            _eat("=");

            CompileExpression();
            
            _eat(";");

            //end tag
            _myWriter.write("</letStatement>\n");
        } catch(Exception e) {
            System.out.println("Could not write to file");
            e.printStackTrace();
        }
    }

    void CompileParameterList() {
        try {
            //start tag
            _myWriter.write("<parameterList>\n");

            while(!_tokenizer.currToken().equals(")"))
                _eat(_tokenizer.currToken());

            //end tag
            _myWriter.write("</parameterList>\n");
        } catch(Exception e) {
            System.out.println("Could not write to file");
            e.printStackTrace();
        }
    }

    void CompileReturnStatement() {
        try{
            //start tag
            _myWriter.write("<returnStatement>\n");

            _eat("return");
            if(!_tokenizer.currToken().equals(";")) {
                CompileExpression();
            }
            _eat(";");

            //end tag
            _myWriter.write("</returnStatement>\n");
        } catch(Exception e) {
            System.out.println("Could not write to file");
            e.printStackTrace();
        }
    }

    void CompileSubroutineBody() {
        try {
            //start tag
            _myWriter.write("<subroutineBody>\n");

            _eat("{");

            while(_tokenizer.currToken().equals("var"))
                CompileVarDec();

            CompileStatements();

            _eat("}");

            //end tag
            _myWriter.write("</subroutineBody>\n");
        } catch(Exception e) {
            System.out.println("Could not write to file");
            e.printStackTrace();
        }
    }

    void CompileSubroutineDec() {
        try {
            //start tag
            _myWriter.write("<subroutineDec>\n");

            //constructor or function or method
            _eat(_tokenizer.currToken());

            //void or type
            _eat(_tokenizer.currToken());

            //subroutine name
            _eat(_tokenizer.currToken());
            
            _eat("(");

            CompileParameterList();

            _eat(")");

            CompileSubroutineBody();

            //end tag
            _myWriter.write("</subroutineDec>\n");
        } catch(Exception e) {
            System.out.println("Could not write to file");
            e.printStackTrace();
        }
    }

    void CompileStatements() {
        try {
            //print start tag
            _myWriter.write("<statements>\n");

            while(!(_tokenizer.symbol() == '}')) {
                if(_tokenizer.keyWord().equals("DO")) CompileDoStatement();
                else if(_tokenizer.keyWord().equals("IF")) CompileIfStatement();
                else if(_tokenizer.keyWord().equals("LET")) CompileLetStatement();
                else if(_tokenizer.keyWord().equals("WHILE")) CompileWhileStatement();
                else if(_tokenizer.keyWord().equals("RETURN")) CompileReturnStatement();
            }
            
            //print end tag
            _myWriter.write("</statements>\n");
        } catch(Exception e) {
            System.out.println("Could not write to file");
            e.printStackTrace();
        }
    }

    void CompileStringConstant() {
        try {
            _myWriter.write("<stringConstant> "+_tokenizer.stringVal()+" </stringConstant>\n");
            _tokenizer.advance();
        } catch(Exception e) {
            System.out.println("Could not write to file");
            e.printStackTrace();
        }
    }

    void CompileSymbol() {
        try {
            _myWriter.write("<symbol> "+_tokenizer.symbol()+" </symbol>\n");
            _tokenizer.advance();
        } catch(Exception e) {
            System.out.println("Could not write to file");
            e.printStackTrace();
        }
    }

    void CompileTerm() {
        try {
            //start tag
            _myWriter.write("<term>\n");
            
            if(_tokenizer.tokenType().equals("INT_CONST") || _tokenizer.tokenType().equals("STRING_CONST") ||
            _tokenizer.tokenType().equals("KEYWORD"))
                _eat(_tokenizer.currToken());

            else if(_tokenizer.tokenType().equals("IDENTIFIER")){

                _eat(_tokenizer.currToken());

                if(_tokenizer.currToken().equals("[")) {
                    _eat("[");

                    CompileExpression();

                    _eat("]");
                } else if(_tokenizer.currToken().equals(".")) {
                    _eat(".");

                    //subroutine name
                    _eat(_tokenizer.currToken());

                    _eat("(");

                    CompileExpressionList();

                    _eat(")");
                } else if(_tokenizer.currToken().equals("(")) {
                    _eat("(");

                    CompileExpression();

                    _eat(")");
                }
            } else if(_tokenizer.currToken().equals("(")) {
                _eat("(");

                CompileExpression();

                _eat(")");
            } else if(_tokenizer.currToken().equals("~") || _tokenizer.currToken().equals("-")) {
                _eat(_tokenizer.currToken());

                CompileTerm();
            }

            //end tag
            _myWriter.write("</term>\n");
        } catch(Exception e) {
            System.out.println("Could not write to file");
            e.printStackTrace();
        }
    }

    void CompileVarDec() {
        try {
            //start tag
            _myWriter.write("<varDec>\n");

            //var
            _eat(_tokenizer.currToken());

            //type
            _eat(_tokenizer.currToken());

            //var name
            _eat(_tokenizer.currToken());

            while(!_tokenizer.currToken().equals(";"))
                _eat(_tokenizer.currToken());

            //end of varDec
            _eat(";");

            //end tag
            _myWriter.write("</varDec>\n");
        } catch(Exception e) {
            System.out.println("Could not write to file");
            e.printStackTrace();
        }
    }

    void CompileWhileStatement() {
        try {
            //start tag
            _myWriter.write("<whileStatement>\n");

            _eat("while");
            _eat("(");

            CompileExpression();
            
            _eat(")");
            _eat("{");
            
            CompileStatements();
            
            _eat("}");

            //end tag
            _myWriter.write("</whileStatement>\n");
        } catch(Exception e) {
            System.out.println("Could not write to file");
            e.printStackTrace();
        }
    }
}
