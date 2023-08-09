// Need to clean up code
#include <iostream>
#include <fstream>
#include "headers/Instruction.h"
#include "headers/Parser.h"
#include "headers/Code.h"
#include "headers/SymbolTable.h"

using namespace std;

string buildSymbolTable(string fileName) {
    SymbolTable stbl;
    ifstream inFile1(fileName + ".asm");
    ofstream outFile1(fileName + "_1.asm");

    string line;
    if(inFile1.is_open()) {
        int n=0;
        while(getline(inFile1, line)) {
            if(outFile1.is_open()) {
                string ss = stbl.pass1(line, n);
                if(ss != "") {
                    n++;
                    outFile1 << ss <<endl;
                }
            } else cout << "Unable to open output file";
        }
    } else cout << "Unable to open input file"; 

    inFile1.close();
    outFile1.close();

    ifstream inFile("ASM/output/AddLPass1.asm");
    ofstream outFile("ASM/output/AddLPass2.asm");
    if(inFile.is_open()) {
        while(getline(inFile, line)) {
            if(outFile.is_open()) {
                string s = stbl.pass2(line);
                outFile << s <<endl;
            } else cout << "Unable to open output file";   
        }
        inFile.close();
        outFile.close();
    } else cout << "Unable to open input file"; 

    return "ASM/output/AddLPass2.asm";
}

int main(int argc, char* argv[]) {
    Instruction inst;
    Parser parser;
    Code code;
    string line;
    string translateCode;

    string inputfile = argv[1];
    inputfile.substr(0, inputfile.find('.'));

    ifstream inFile (buildSymbolTable(inputfile));
    ofstream outFile (inputfile.substr(0, inputfile.find('.'))+".hack");
    if (inFile.is_open()) {
        while ( getline(inFile, line) ) {
            inst = parser.parse(line);
            translateCode = code.translate(inst);
            if(translateCode != "no-op") {
                if (outFile.is_open()) {
                    outFile << translateCode << "\n";
                } else cout << "Unable to open output file";
            }
        }
        inFile.close();
        outFile.close();
    } else cout << "Unable to open input file"; 

    return 0;
}