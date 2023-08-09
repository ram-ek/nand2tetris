#include <fstream>
class Parser {
    vector<string> tokenize(string s, string del = " ") {
        vector<string> res;
        int start, end = -1*del.size();
        string str;
        do {
            start = end + del.size();
            end = s.find(del, start);
            str = s.substr(start, end - start);
            str.erase(remove_if(str.begin(), str.end(), ::isspace), str.end());
            res.push_back(str);
        } while (end != -1);
        
        return res;
    }

    std::string trim(const std::string& str,
                 const std::string& whitespace = " \t")
    {
        const auto strBegin = str.find_first_not_of(whitespace);
        if (strBegin == std::string::npos)
            return ""; // no content

        const auto strEnd = str.find_last_not_of(whitespace);
        const auto strRange = strEnd - strBegin + 1;

        return str.substr(strBegin, strRange);
    }

    string cmdType;
    Parser(ifstream vmFile) {

    }

    bool hasMoreCommands(){
        return !vmFile.eof();
    }

    void advance() {
        getline(vmFile, line);
        string vmLine = tokenize(line, "//");
    }

    string commandType() {

    }

    string arg1() {

    }

    int arg2() {

    }
};