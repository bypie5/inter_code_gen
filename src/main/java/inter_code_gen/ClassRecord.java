package inter_code_gen;

import java.util.Iterator;
import java.util.List;
import java.util.ArrayList;

public class ClassRecord {

    public String classname;
    public List<String> fields;
    public VTable v_table;

    public ClassRecord(String classname) {
        this.classname = classname;
        fields = new ArrayList<>();
        v_table = new VTable(classname + "vtable");
    }

    public void copyFieldsFrom(ClassRecord cr) {
        for (int i = 0; i < cr.fields.size(); i++) {
            this.fields.add(cr.fields.get(i));
        }
    }

    public void addField(String name) {
        fields.add(name);
    }

    // Offset starts at one because the address of the vtable gets stored in zero
    public int getOffset(String field) {
        int offset = 1;
        Iterator<String> fieldIter = fields.iterator();
        while (fieldIter.hasNext()) {
            if (fieldIter.next().equals(field))
                return offset;
            offset++;
        }

        return -1;
    }
}
