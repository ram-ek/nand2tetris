class Code {
private:
    // Destination translate
    string destTranslate(string str) {
        if(str == "") return "000";
        else if(str == "M") return "001";
        else if(str == "D") return "010";
        else if(str == "MD" || str == "DM") return "011";
        else if(str == "A") return "100";
        else if(str == "AM" || str == "MA") return "101";
        else if(str == "AD" || str == "DA") return "110";
        else if(str == "AMD" || str == "ADM" || str == "MAD" || str == "MDA" || str == "DAM" || str == "DMA") return "111";
        else return "error";
    }

    // Computation translate
    string compTranslate(string str) {
        if(str == "0") return "0101010";
        else if(str == "1") return "0111111";
        else if(str == "-1") return "0111010";
        else if(str == "D") return "0001100";
        else if(str == "A") return "0110000";
        else if(str == "M") return "1110000";
        else if(str == "!D") return "0001101";
        else if(str == "!A") return "0110001";
        else if(str == "!M") return "1110001";
        else if(str == "-D") return "0001111";
        else if(str == "-A") return "0110011";
        else if(str == "-M") return "1110011";
        else if(str == "D+1" || str == "1+D") return "0011111";
        else if(str == "A+1" || str == "1+A") return "0110111";
        else if(str == "M+1" || str == "1+M") return "1110111";
        else if(str == "D-1") return "0001110";
        else if(str == "A-1") return "0110010";
        else if(str == "M-1") return "1110010";
        else if(str == "D+A" || str == "A+D") return "0000010";
        else if(str == "D+M" || str == "M+D") return "1000010";
        else if(str == "D-A") return "0010011";
        else if(str == "D-M") return "1010011";
        else if(str == "A-D") return "0000111";
        else if(str == "M-D") return "1000111";
        else if(str == "D&A" || str == "A&D") return "0000000";
        else if(str == "D&M" || str == "M&D") return "1000000";
        else if(str == "D|A" || str == "A|D") return "0010101";
        else if(str == "D|M" || str == "M|D") return "1010101";
        else return "error";
    }

    // Jump translate
    string jumpTranslate(string str) {
        if(str == "") return "000";
        else if(str == "JGT") return "001";
        else if(str == "JEQ") return "010";
        else if(str == "JGE") return "011";
        else if(str == "JLT") return "100";
        else if(str == "JNE") return "101";
        else if(str == "JLE") return "110";
        else if(str == "JMP") return "111";
        else return "error";
    }

public:
    string translate(Instruction inst) {
        if(inst.getInstType() == "C") {
            string dest = destTranslate(inst.getDest());
            string comp = compTranslate(inst.getComp());
            string jump = jumpTranslate(inst.getJump());

            return "111" + comp + dest + jump;
        } else if(inst.getInstType() == "A") {
            return '0' + bitset<15>(stoi(inst.getComp())).to_string();
        } else if(inst.getInstType() == "no-op") return "no-op";
        else return "error";
    }
};