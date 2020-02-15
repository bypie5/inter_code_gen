package syntax_checker;

import java.util.List;

public class ClassBinder extends Binder {

    public String classname;
    public SymbolTable methods;
    public String parent;

    public ClassBinder(String c) {
        classname = c;
        methods = new SymbolTable();
    }

    public String getIdType(String id, String currMethod) {
        List<String> params = myItems.getItems();
        if (params.contains(id)) {
            ClassBinder cb = (ClassBinder) myItems.get(Symbol.symbol(id));
            return cb.classname;
        }
        List<String> methodNames = methods.getItems();
        if (methodNames.contains(currMethod)) {
            MethodsBinder mb = (MethodsBinder) methods.get(Symbol.symbol(currMethod));

            if (mb.myItems.get(Symbol.symbol(id)) != null)
                return ((ClassBinder) mb.myItems.get(Symbol.symbol(id))).classname;
            else {
                for (int i = 0; i < mb.params.size(); i++) {
                    if (mb.params.get(i).equals(id)) {
                        return mb.paramTypes.get(i);
                    }
                }
            }

        }
        return null;
    }
}
