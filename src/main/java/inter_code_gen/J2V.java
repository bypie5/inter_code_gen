package inter_code_gen;

import parser.MiniJavaParser;
import syntax_checker.*;
import syntaxtree.*;
import visitor.GJVisitor;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

public class J2V {

    public static List<ClassRecord> classRecords;

    // The object that facilitate Vapor code generation
    static GenerateVapor gv = new GenerateVapor();

    public static void generateCode() {

        //MiniJavaParser parser = new MiniJavaParser(System.in);

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

                Iterator<String> methods = currClass.methods.getItems().iterator();
                while (methods.hasNext()) {
                    String currMethod = methods.next();
                    String funcName = currClassname + "_" + currMethod;

                    currRecord.v_table.addFunction(funcName);
                }
            }

            // Puts the v_tables and class records into a text buffer
            gv.setupTables(classRecords);

            // Get ready for another round of visitors
            VaporVisitor<String, String> vv = new VaporVisitor<>();
            vv.gv = gv; // Pass the code buffer to the VaporVisitor
            try {
                //Goal root = parser.Goal();
                Typecheck.root.accept(vv, "");
            } catch (Exception e) {
                System.out.println("ERROR: " + e);
                e.printStackTrace();
            }

            gv.printBuffer();
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

    static void inspectCR() {
        // Inspect ClassRecords
        Iterator<ClassRecord> crIterator = classRecords.iterator();
        while (crIterator.hasNext()) {
            ClassRecord curr = crIterator.next();
            System.out.println(curr.classname);

            System.out.println("FIELDS");

            Iterator<String> fields = curr.fields.iterator();
            while (fields.hasNext()) {
                System.out.println("     " + fields.next());
            }

            System.out.println("METHODS");

            Iterator<String> methods = curr.v_table.functions.iterator();
            while (methods.hasNext()) {
                System.out.println("     " + methods.next());
            }
        }
    }
}
