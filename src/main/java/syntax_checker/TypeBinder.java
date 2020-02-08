package syntax_checker;

public class TypeBinder extends Binder {
    public TypeBinder() {

    }
}

class IntBinder extends TypeBinder {

}

class BoolBinder extends TypeBinder {

}

class ArrayBinder extends TypeBinder {

}

class ClassTypeBinder extends TypeBinder {
    String classname;
}
