class ParamAccess {
    public static void main(String[] a){
        System.out.println(new A().run());
    }
}

class A {
    int x;

    public int run() {
        int a;
        int b;
        b = this.setX(1);
        a = 2;
        b = x + 3;
        return a + b;
    }

    public int setX (int f) {
        x = f;
        return 0;
    }
}
