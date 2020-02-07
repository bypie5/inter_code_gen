package inter_code_gen;

import java.util.List;
import java.util.ArrayList;

public class ClassRecord {

    public String classname;
    public List<String> fields;
    public VTable v_table;

    public ClassRecord(String classname) {
        this.classname = classname;
        fields = new ArrayList<>();
        v_table = new VTable();
    }

    public void copyFieldsFrom(ClassRecord cr) {
        for (int i = 0; i < cr.fields.size(); i++) {
            this.fields.add(cr.fields.get(i));
        }
    }

    public void addField(String name) {
        fields.add(name);
    }
}
