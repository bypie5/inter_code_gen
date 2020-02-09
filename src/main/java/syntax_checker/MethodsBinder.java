package syntax_checker;

import java.util.ArrayList;
import java.util.List;

public class MethodsBinder extends Binder {

    TypeBinder type;
    public int paramCount;
    List<String> paramTypes;

    public MethodsBinder() {
        paramTypes = new ArrayList<>();
    }
}
