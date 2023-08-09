// Need to add remove comment function
class Parser {
private:
    // String split logic
    vector<string> tokenize(string s, string del = " ") {
        vector<string> res;
        int start, end = -1*del.size();
        string str;
        do {
            start = end + del.size();
            end = s.find(del, start);
            str = s.substr(start, end - start);
            str.erase(remove_if(str.begin(), str.end(), ::isspace), str.end());  //[](unsigned char c){ return isspace(c); }
            res.push_back(str);
        } while (end != -1);
        
        return res;
    }

public:
    Instruction parse(string line) {
        Instruction instruction;
        
        // No-op
        vector<string> instLine = tokenize(line, "//");
        if(instLine[0].size() == 0) {
            instruction.setInstType("no-op");
            return instruction;
        }
        
        // A - Instruction
        if(instLine[0][0] == '@') {
            instruction.setInstType("A");
            instruction.setComp(instLine[0].substr(1, instLine[0].size()));
            return instruction;
        }

        // C - Instruction
        vector<string> subInst;
        instruction.setInstType("C");
        if (instLine[0].find(';') != std::string::npos) {
            subInst = tokenize(instLine[0], ";");
            instruction.setJump(subInst[1]);
            
        } else {
            instruction.setJump("");
            subInst.push_back(instLine[0]);
        }
        
        if(subInst[0].find('=') != std::string::npos) {
            subInst = tokenize(subInst[0], "=");
            instruction.setDest(subInst[0]);
            instruction.setComp(subInst[1]);
        } else {
            instruction.setDest("");
            instruction.setComp(subInst[0]);
        }

        return instruction;
    }    
};