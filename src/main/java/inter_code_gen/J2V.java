package inter_code_gen;

import syntax_checker.*;
import syntaxtree.Type;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

public class J2V {

    public static void generateCode() {
        if (!Typecheck.typeCheck()) {
            // Program is not valid
            // Do not proceed further

        } else {
            // Construct a class graph
            ClassGraph gc = new ClassGraph();
            Iterator<String> rawClasses = Typecheck.symbolTable.getItems().iterator();
            while (rawClasses.hasNext()) {
                String currClassname = rawClasses.next();
                ClassBinder currClass = (ClassBinder) Typecheck.symbolTable.get(Symbol.symbol(currClassname));

                gc.addEdge(currClassname, currClass.parent);
            }

            gc.print();

            // Topologically sort class graph

            // Construct class records
            List<ClassRecord> classRecords = new ArrayList<>();

            Iterator<String> classes = Typecheck.symbolTable.getItems().iterator();
            while(classes.hasNext()) {
                String currClassname = classes.next();
                ClassBinder currClass = (ClassBinder) Typecheck.symbolTable.get(Symbol.symbol(currClassname));

                ClassRecord currRecord = new ClassRecord(currClassname);

                // Fields from parent classes

                // Explicit fields
                Iterator<String> fields = currClass.myItems.getItems().iterator();
                while (fields.hasNext()) {
                    currRecord.addField(fields.next());
                }

                /*Iterator<String> methods = currClass.methods.getItems().iterator();
                while (methods.hasNext()) {
                    String currMethod = methods.next();
                }*/
            }
        }
    }

    public static void main(String args[]) {


    }
}
