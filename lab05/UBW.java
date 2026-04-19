import java.util.*;

class UBW{

    public static double sumElements(List<? extends Number> list) {

        double sum = 0.0;
        for (Number n : list) {
            sum += n.doubleValue();
        }

        return sum;
    }


    public static void main(String[] args){

        List<Integer> intList = Arrays.asList(1, 2, 3, 4, 5);
        List<Double> dblList = Arrays.asList(1.5, 2.5, 3.0);
        List<Long> longList = Arrays.asList(100L, 200L, 300L);

        System.out.println("Suma intList: " + UBW.sumElements(intList));
        System.out.println("Suma dblList: " + UBW.sumElements(dblList));
        System.out.println("Suma longList: " + UBW.sumElements(longList));

    }
}



