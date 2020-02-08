package syntax_checker;

import java.util.List;
import java.util.ArrayList;

class Bucket {
    Symbol key;
    Binder binding;
    Bucket next;

    Bucket(Symbol k, Binder b, Bucket n) {
        key = k;
        binding = b;
        next = n;
    }
}

class HashT {
    final int SIZE = 256;
    Bucket table[] = new Bucket[SIZE];

    private int hash(Symbol k) {
        String s = k.toString();
        int h = 0;
        for (int i = 0; i < s.length(); i++)
            h = h*65599+s.charAt(i);
        return h;
    }

    void insert(Symbol s, Binder b) {
        int index=hash(s)%SIZE;
        index = (index < 0) ? index*-1 : index;
        table[index] = new Bucket(s, b, table[index]);
    }

    Binder lookup(Symbol s) {
        int index=hash(s)%SIZE;
        index = (index < 0) ? index*-1 : index;
        for (Bucket b = table[index]; b != null; b = b.next) {
            if (s.eq(b.key)) {
                return b.binding;
            }
        }

        return null;
    }

    void pop(Symbol s) {
        int index = hash(s)%SIZE;
        table[index] = table[index].next;
    }

    public void print() {
        for (int i = 0; i < table.length; i++) {
            Bucket curr = table[i];
            while (curr != null) {
                System.out.println(curr.key.toString());
                curr = curr.next;
            }
        }
    }


    public List<String> getAllItems() {
        List<String> items = new ArrayList<>();
        for (int i = 0; i < table.length; i++) {
            Bucket curr = table[i];
            while (curr != null) {
                items.add(curr.key.toString());
                curr = curr.next;
            }
        }
        return items;
    }
}

public class SymbolTable {

    HashT hashT;

    public SymbolTable() {
        hashT = new HashT();
    }

    public void put(Symbol key, Binder value) {
        hashT.insert(key, value);
    }

    public Binder get(Symbol key) {
        return hashT.lookup(key);
    }

    public boolean alreadyExists(Symbol key) {
        return !(get(key) == null);
    }

    public void print() {
        hashT.print();
    }

    public List<String> getItems() {
        return hashT.getAllItems();
    }
}
