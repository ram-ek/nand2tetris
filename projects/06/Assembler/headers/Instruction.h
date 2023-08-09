#include <string>
using namespace std;
class Instruction {
private:
    string instType, dest, comp, jump;
    
public:
    // getters
    string getInstType() {
        return instType;
    }
    string getDest() {
        return dest;
    }
    string getComp() {
        return comp;
    }
    string getJump() {
        return jump;
    }
    
    // setters
    void setInstType(string instType) {
        this->instType = instType;
    }
    void setDest(string dest) {
        this->dest = dest;
    }
    void setComp(string comp) {
        this->comp = comp;
    }
    void setJump(string jump) {
        this->jump = jump;
    }
};