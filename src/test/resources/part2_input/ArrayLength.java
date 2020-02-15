class Main {
    public static void main(String[] a){
        System.out.println(new A().test());
    }
}

class A {
    public int test() {
        int[] a;

        a = new int[100];

        return a.length;
    }
}
