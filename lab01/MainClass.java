public class MainClass{
    public MainClass(){
        System.out.println("MainClass Constructor");
    }

    static void method1(){
        System.out.println("MainClass Method 1");
    }

    public static void main(String[] args){
        MainClass mymain = new MainClass();
        Class1 myclass = new Class1();
        
        mymain.method1();
        myclass.method1();
    }
}
