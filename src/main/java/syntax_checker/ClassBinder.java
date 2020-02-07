package syntax_checker;

public class ClassBinder extends Binder {

    public String classname;
    public SymbolTable methods;
    public String parent;

    public ClassBinder(String c) {
        classname = c;
        methods = new SymbolTable();
    }
}
