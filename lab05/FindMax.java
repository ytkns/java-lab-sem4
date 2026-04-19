class FindMax<T extends Number, U extends Number>{
    T t;
    U u;
    public FindMax(T x, U y) {  t=x; u=y;}
    public double getMax(){
        return Math.max(t.doubleValue(), u.doubleValue());
    }

    public static void main(String[] args){
        FindMax<Integer, Double> intOrDouble = new FindMax<Integer, Double>(5, 9.8);
        FindMax<Double, Long> doubleOrLong = new FindMax<Double, Long>(9.8, 300L);

        System.out.println("ans1 = " + intOrDouble.getMax());
        System.out.println("ans2 = " + doubleOrLong.getMax());
    }

}