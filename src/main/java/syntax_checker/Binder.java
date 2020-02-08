package syntax_checker;

public abstract class Binder {

    public SymbolTable myItems;

    public Binder() {
        myItems = new SymbolTable();
    }

    public boolean addSymbol(Symbol key, Binder b) {
        if (myItems.get(key) == null) {
            myItems.put(key, b);
            return true;
        }
        return false;
    }
}
