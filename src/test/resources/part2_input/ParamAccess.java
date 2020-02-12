class ParamAccess {
    public static void main(String[] a){
        System.out.println(new A().run());
    }
}

class A {
    int x;
    int y;

    public int run() {
        int a;
        int b;
        y = 100;
        b = this.setX(1); // x = 1
        a = 2;
        b = x + 3; // 1 + 3
        b = y + b; // 100 + 4
        return b; // 104
    }

    public int setX (int f) {
        x = f;
        System.out.println(y);
        return x;
    }
}
