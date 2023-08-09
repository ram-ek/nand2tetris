#include<iostream>
#include <string>
#include <unordered_map>
#include <regex>
#include <fstream>

using namespace std;

class SymbolTable {
private:
    int n;
    unordered_map<string, int> mp;

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


    void getBtwString(std::string oStr, std::string sStr1, std::string sStr2, std::string &rStr)
    {  
        int start = oStr.find(sStr1);   
        if (start >= 0) {       
        string tstr = oStr.substr(start + sStr1.length());        
        int stop = tstr.find(sStr2);      
        if (stop >1)          
            rStr = oStr.substr(start + sStr1.length(), stop);
        else
            rStr ="error";  
        }
        else
            rStr = "error";
    }

public:

    SymbolTable() {
        mp["R0"] = 0; mp["R1"] = 1; mp["R2"] = 2; mp["R3"] = 3;
        mp["R4"] = 4; mp["R5"] = 5; mp["R6"] = 6; mp["R7"] = 7;
        mp["R8"] = 8; mp["R9"] = 9; mp["R10"] = 10; mp["R11"] = 11;
        mp["R12"] = 12; mp["R13"] = 13; mp["R14"] = 14; mp["R15"] = 15;

        mp["SCREEN"] = 16384; mp["KBD"] = 24576;

        mp["SP"] = 0; mp["LCL"] = 1; mp["ARG"] = 2; mp["THIS"] = 3; mp["THAT"] = 4;

        n = 16;
    }

    string pass1(string line, int lineNum) {
        string key;
        vector<string> str = tokenize(line, "//");
        str[0].erase(remove_if(str[0].begin(), str[0].end(), ::isspace), str[0].end());
        // line.erase(remove_if(line.begin(), line.end(), ::isspace), line.end());
        if(str[0][0] == '(') {
            getBtwString(line, "(", ")", key);
            mp[key] = lineNum;

            return "";
        }

        return str[0];
    }

    string pass2(string line) {
        string key;
        vector<string> str = tokenize(line, "//");
        str[0].erase(remove_if(str[0].begin(), str[0].end(), ::isspace), str[0].end());
        if(str[0][0] == '@' && isalpha(str[0][1])) {
            // cout<<str[0];
            // getBtwString(str[0].substr(str[0][1], str.end()), "@", "\r\n", key);
            // check for symbols only
            key = str[0].substr(1, str.size()-2);
            // cout<<"key: "<<key;
            if(mp.find(key) == mp.end()) mp[key] = n++;

            return '@' + to_string(mp[key]);
        } 

        return str[0];
    }
};

// int main() {
//     SymbolTable stbl;
//     ifstream inFile("../ASM/input/AddL.asm");
//     ofstream outFile("../ASM/output/AddL.asm");

//     string line;
//     if(inFile.is_open()) {
//         while(getline(inFile, line)) {
//             stbl.pass1(line);
//         }
//     }

//     inFile.clear();
//     inFile.seekg(0);

//     if(inFile.is_open()) {
//         while(getline(inFile, line)) {
//             if(outFile.is_open()) {
//                 outFile << stbl.pass2(line)<<endl;
//             } else cout << "Unable to open output file";   
//         }
//         inFile.close();
//         outFile.close();
//     } else cout << "Unable to open input file"; 

//     return 0;
// }