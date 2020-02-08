package inter_code_gen;

import java.util.ArrayList;
import java.util.List;
import java.util.Iterator;

public class GenerateVapor {

    List<String> buffer;

    public GenerateVapor() {
        buffer = new ArrayList<>();
    }

    public void setupTables(List<ClassRecord> classRecords) {
        Iterator<ClassRecord> iter = classRecords.listIterator();
        while (iter.hasNext()) {
            initClassRecord(iter.next());
        }
    }

    public void printBuffer() {
        Iterator<String> iter = buffer.iterator();
        while (iter.hasNext()) {
            System.out.println(iter.next());
        }
    }

    void initClassRecord(ClassRecord cr) {
        initVTable(cr.v_table);

        buffer.add("const " + cr.classname);
        buffer.add("    :" + cr.v_table.name); // v_table pointer
    }

    void initVTable(VTable vt) {
        buffer.add("const " + vt.name);

        // Function pointers
        Iterator<String> funcIter = vt.functions.iterator();
        while (funcIter.hasNext()) {
            buffer.add("    :" + funcIter.next());
        }

        buffer.add("");
    }
}
