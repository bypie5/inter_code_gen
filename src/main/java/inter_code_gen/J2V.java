package inter_code_gen;

import syntax_checker.*;

public class J2V {

    public static void generateCode() {
        if (!Typecheck.typeCheck()) {
            // Program is not valid
            // Do not proceed further
        }

        Typecheck.symbolTable.print();
    }


    public static void main(String args[]) {

    }
}