class WrongParams{
    public static void main(String[] a){
        System.out.println(new WP().Test(1,2,3,4,5,6));
    }
}

class WP {
    A a;
    B b;
    C c;

    public int Test(int p1, int p2, int p3 , int p4, int p5, int p6){
        System.out.println(p1);
        System.out.println(p2);
        System.out.println(p3);
        System.out.println(p4);
        System.out.println(p5);
        System.out.println(p6);
        return this.Other(a, p2, false, b, p6, c);
    }

    public int Other(A a, int b, boolean bool, A a2, int c, A wow) {
        return 0;
    }
}

class A {

}

class B extends A {

}

class C extends B {

}
