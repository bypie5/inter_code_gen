package syntax_checker;

import java.util.ArrayList;
import java.util.List;

public class MethodsBinder extends Binder {

    TypeBinder type;
    public int paramCount;
    public List<String> paramTypes;
    public List<String> params;

    public MethodsBinder() {
        paramTypes = new ArrayList<>();
        params = new ArrayList<>();
    }
}
