import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JackTokenizer {
    private static final String KEYWORD = "KEYWORD", SYMBOL = "SYMBOL", IDENTIFIER = "IDENTIFIER", INT_CONST = "INT_CONST",
    STRING_CONST = "STRING_CONST";
    // , CLASS = "CLASS", METHOD = "METHOD", FUNCTION = "FUNCTION", CONSTRUCTOR = "CONSTRUCTOR",
    // INT = "INT", BOOLEAN = "BOOLEAN", CHAR = "CHAR", VOID = "VOID", VAR = "VAR", STATIC = "STATIC", FIELD = "FIELD", LET = "LET",
    // DO = "DO", IF = "IF", ELSE = "ELSE", WHILE = "WHILE", RETURN = "RETURN", TRUE = "TRUE", FALSE = "FALSE", NULL = "NULL", THIS = "THIS";

    Scanner _myReader;
    String _tokenType, _currToken, _data="", COMMENT_PATTERN;
    Pattern EMPTY_TEXT_PATTERN, KEY_WORD_PATTERN, SYMBOL_PATTERN, DIGIT_PATTERN, STRING_PATTERN, IDENTIFIER_PATTERN;

    public JackTokenizer(String inputFilename) {
        try {
            File inputFile = new File(inputFilename);
            _myReader = new Scanner(inputFile);
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }

        _initializePatterns();
        _makeData();
        _myReader.close();
        _removeComment();
    }

    private void _initializePatterns() {
        COMMENT_PATTERN = "\r\n|(//.*)|(/\\*([^*]|[\r\n]|(\\*+([^*/]|[\r\n])))*\\*+/)";
        EMPTY_TEXT_PATTERN = Pattern.compile("\s*");
        KEY_WORD_PATTERN = Pattern.compile("^\s*("+
                            "class|constructor|function|method|static|field"+
                            "|var|int|char|boolean|void|true|false|null|this|"+
                            "let|do|if|else|while|return)\s*");
        SYMBOL_PATTERN = Pattern.compile("^\s*([{}()/\\[/\\].,;+/\\-*/&|<>=~])\s*");
        DIGIT_PATTERN = Pattern.compile("^\s*(\\d+)\s*");
        STRING_PATTERN = Pattern.compile("^\s*\"(.*?)\"\s*");
        IDENTIFIER_PATTERN = Pattern.compile("^\s*([a-zA-Z_][a-zA-Z1-9_]*)\s*");
    }

    private void _makeData() { while(_myReader.hasNextLine()) _data += _myReader.nextLine()+"\r\n"; }

    private void _removeComment() { _data = _data.replaceAll(COMMENT_PATTERN, ""); }

    public boolean hasMoreTokens() {
        Pattern empty = Pattern.compile("\s*");
        Matcher matcher = empty.matcher(_data);
        return !matcher.matches();
    }

    public void advance() {
        Matcher matcher;

        if(hasMoreTokens()) {
            matcher = KEY_WORD_PATTERN.matcher(_data);
            if(matcher.find()) {
                _data = matcher.replaceFirst("");
                _tokenType = KEYWORD;
                _currToken = matcher.group(1);
            } else {
                matcher = SYMBOL_PATTERN.matcher(_data);
                if(matcher.find()) {
                    _data = matcher.replaceFirst("");
                    _tokenType = SYMBOL;
                    _currToken = matcher.group(1);
                } else {
                    matcher = DIGIT_PATTERN.matcher(_data);
                    if(matcher.find()) {
                        _data = matcher.replaceFirst("");
                        _tokenType = INT_CONST;
                        _currToken = matcher.group(1);
                    } else {
                        matcher = STRING_PATTERN.matcher(_data);
                        if(matcher.find()) {
                            _data = matcher.replaceFirst("");
                            _tokenType = STRING_CONST;
                            _currToken = matcher.group(1);
                        } else {
                            matcher = IDENTIFIER_PATTERN.matcher(_data);
                            if(matcher.find()) {
                                _data = matcher.replaceFirst("");
                                _tokenType = IDENTIFIER;
                                _currToken = matcher.group(1);
                            }
                        }
                    }
                }
            }
        }
    }

    public String currToken() { return _currToken; }
    
    public String tokenType() { return _tokenType; }

    public String keyWord() { return _currToken.toUpperCase(); }

    public char symbol() { return _currToken.charAt(0); }

    public String identifier() { return _currToken; }

    public int intVal() { return Integer.parseInt(_currToken); }

    public String stringVal() { return _currToken; }
}