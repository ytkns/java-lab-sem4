public class main{
    static double a = -9.81d;
    double t;
    double x0;
    double v0;

    static void method(double t, double x0, double v0){
        double ans = (a*t*t/2 + t*v0 + x0);
        System.out.println("pozycja ciala = " + ans);
    }

    public static void main(String[] args){
        method(2.2, 257.0, 63*1000/3600);
    }
}