package inter_code_gen;

import java.util.Iterator;
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

    public int getFunctionOffset(String key) {
        int count = 0;
        Iterator<String> iter = functions.iterator();
        while(iter.hasNext()) {
            String curr = iter.next();

            if (curr.contains(key))
                return count;

            count++;
        }

        System.out.println("VTable does not contain " + key);
        return -1;
    }

    public String getFunctionLabel(int index) {
        return functions.get(index);
    }
}
