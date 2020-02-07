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

            // Construct class records
            List<ClassRecord> classRecords = new ArrayList<>();

            // Topologically sort class graph
            Iterator<String> classes = gc.topologicalSort().iterator();
            while(classes.hasNext()) {
                String currClassname = classes.next();

                System.out.println(currClassname);

                ClassBinder currClass = (ClassBinder) Typecheck.symbolTable.get(Symbol.symbol(currClassname));

                ClassRecord currRecord = new ClassRecord(currClassname);
                classRecords.add(currRecord);

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

            Iterator<ClassRecord> crIterator = classRecords.iterator();
            while (crIterator.hasNext()) {
                System.out.println(crIterator.next().classname);
            }
        }
    }

    public static void main(String args[]) {


    }
}
