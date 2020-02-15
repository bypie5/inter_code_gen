class FunctionCall {
    public static void main(String[] a){
        System.out.println(new A().run());
    }
}

class A {
    B b;
    int a;
    public int run() {
        int result;

        b = new B();
        a = 70;
        result = b.test();
        System.out.println(a);
        System.out.println(result);
        return 66;
    }
}

class B {
    int b;

    public int test() {
        b = 68;
        return b;
    }
}
