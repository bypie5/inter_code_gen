package inter_code_gen;

import java.util.List;
import java.util.ArrayList;

public class VTable {

    String name;

    // Stores function labels
    public List<String> functions;

    public VTable(String name) {
        this.name = name;
        functions = new ArrayList<>();
    }

    public void addFunction(String label) {
        functions.add(label);
    }
}
