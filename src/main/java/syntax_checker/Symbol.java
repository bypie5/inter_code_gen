package syntax_checker;

import java.util.Dictionary;
import java.util.Hashtable;

public class Symbol {
    private String name;
    private Symbol(String n) {
        name=n;
    }
    private static Dictionary dict = new Hashtable();

    public String toString() {
        return name;
    }

    public static Symbol symbol(String n) {
        String u = n.intern();
        Symbol s = (Symbol) dict.get(u);
        if (s == null) {
            s = new Symbol(u);
            dict.put(u, s);
        }

        return s;
    }

    public boolean lt(Symbol lhs) {
        return this.hashCode() < lhs.hashCode();
    }

    public boolean gt(Symbol lhs) {
        return this.hashCode() > lhs.hashCode();
    }

    public boolean eq(Symbol lhs) {
        return this.hashCode() == lhs.hashCode();
    }

    public boolean lte(Symbol lhs) {
        return this.lt(lhs) || this.eq(lhs);
    }

    public boolean gte(Symbol lhs) {
        return this.gt(lhs) || this.eq(lhs);
    }
}
