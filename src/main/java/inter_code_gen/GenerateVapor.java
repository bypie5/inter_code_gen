package inter_code_gen;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

public class GenerateVapor {

    List<String> buffer;
    int intentLevel;

    public GenerateVapor() {
        buffer = new ArrayList<>();
        intentLevel = 0;
    }

    public void setupTables(List<ClassRecord> classRecords) {
        Iterator<ClassRecord> iter = classRecords.listIterator();
        while (iter.hasNext()) {
            initClassRecord(iter.next());
        }
    }

    public void printBuffer() {

        addLine(""); // New line to close off the file

        Iterator<String> iter = buffer.iterator();
        while (iter.hasNext()) {
            System.out.println(iter.next());
        }
    }

    public void addLine(String line) {
        String prefix = "";
        for (int i = 0; i < intentLevel * 2; i++)
            prefix += " ";

        buffer.add(prefix + line);
    }

    public void increaseIndent() {
        intentLevel++;
    }

    public void descreaseIndent() {
        if (intentLevel != 0)
            intentLevel--;
    }

    void initClassRecord(ClassRecord cr) {
        initVTable(cr.v_table);

        // Mutable data for class fields
        addLine("var " + cr.classname);
        increaseIndent();
        addLine(":" + cr.v_table.name); // v_table pointer
        descreaseIndent();
        addLine("");
    }

    void initVTable(VTable vt) {
        addLine("const " + vt.name);

        increaseIndent();

        // Function pointers
        Iterator<String> funcIter = vt.functions.iterator();
        while (funcIter.hasNext()) {
            String funcName = funcIter.next();
            if (funcName.contains("main")) {
                addLine(":Main");
            } else {
                addLine(":" + funcName);
            }
        }

        descreaseIndent();
        addLine("");
    }
}
