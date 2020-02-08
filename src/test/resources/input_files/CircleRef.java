class CircleRef{
    public static void main(String[] a){
        System.out.println(new C1().Test());
    }
}

class C1 extends C2 {
    public int Test() {
        return 0;
    }

}

class C2 extends C3 {

}

class C3 extends C4 {

}

class C4 extends C5 {

}

class C5 extends C2 {

}
