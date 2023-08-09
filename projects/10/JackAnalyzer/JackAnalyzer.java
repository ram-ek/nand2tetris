public class JackAnalyzer {
    public static void main(String[] args) {
        String inputFilename = args[0];

        JackTokenizer tokenizer = new JackTokenizer(inputFilename);

        new CompileEngine(tokenizer, inputFilename.split("[.]")[0]+".xml");
    }
}
