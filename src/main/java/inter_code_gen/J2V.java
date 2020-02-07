package inter_code_gen;

import syntax_checker.*;
import syntaxtree.Type;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

public class J2V {

    public static List<ClassRecord> classRecords;

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
            classRecords = new ArrayList<>();

            // Topologically sort class graph
            Iterator<String> classes = gc.topologicalSort().iterator();
            while(classes.hasNext()) {
                String currClassname = classes.next();

                ClassBinder currClass = (ClassBinder) Typecheck.symbolTable.get(Symbol.symbol(currClassname));

                ClassRecord currRecord = new ClassRecord(currClassname);
                classRecords.add(currRecord);

                // Fields from parent classes
                // If topo-sort works, then the parent's CR should already contain the data
                if (currClass.parent != null) {
                    currRecord.copyFieldsFrom(findClassRecord(currClass.parent));
                }

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

            // Inspect ClassRecords
            Iterator<ClassRecord> crIterator = classRecords.iterator();
            while (crIterator.hasNext()) {
                ClassRecord curr = crIterator.next();
                System.out.println(curr.classname);

                Iterator<String> fields = curr.fields.iterator();
                while (fields.hasNext()) {
                    System.out.println("     " + fields.next());
                }
            }
        }
    }

    public static void main(String args[]) {
        generateCode();
    }

    static ClassRecord findClassRecord(String name) {
        Iterator<ClassRecord> crIterator = classRecords.iterator();
        while (crIterator.hasNext()) {
            ClassRecord curr = crIterator.next();
            if (curr.classname.equals(name))
                return curr;
        }

        return null;
    }
}
