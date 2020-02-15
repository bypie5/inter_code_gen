class Call {
    public static void main(String[] a){
        System.out.println(new A().run());
    }
}

class A {
    public int run() {
        B b;
        B c;
        int dump;

        b = new B();
        c = new B();
        dump = b.setT(false);
        dump = c.setT(false);
        if (!c.bool() &&
            !b.boolF() )
            System.out.println(42);
        else
            System.out.println(0);

        return 99;
    }
}

class B {
    boolean t;

    public boolean bool() {
        return false;
    }

    public boolean boolF() {
        return t;
    }

    public int setT(boolean tVal) {
        t = tVal;
        return 0;
    }
}
