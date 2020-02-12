class ParamAccess {
    public static void main(String[] a){
        System.out.println(new A().run());
    }
}

class A {
    int x;
    int y;
    int[] arr;

    public int run() {
        int a;
        int b;

        arr = new int[100];
        arr[99] = 69;
        arr[98] = 70;
        arr[97] = 71;
        System.out.println(arr[98]);
        System.out.println(arr[97]);
        y = arr[99];
        System.out.println(arr[21]);
        b = this.setX(1); // x = 1
        System.out.println(arr[21]);
        y = 100;
        a = 2;
        b = x + 3; // 1 + 3
        b = y + b; // 100 + 4
        return b; // 104
    }

    public int setX (int f) {
        x = f;
        System.out.println(y);
        arr[21] = 202020;
        return x;
    }
}
