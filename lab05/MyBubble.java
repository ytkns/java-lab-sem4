import java.util.*;

class MyBubble<T>{

    public int myCompare(T a, T b){
        if (a instanceof String && b instanceof String){
            String s1 = (String) a;
            String s2 = (String) b;
            
            return s1.length() - s2.length();
        }

        if (a instanceof Integer && b instanceof Integer){
            int countX = countDigits((Integer) a);
            int countY = countDigits((Integer) b);

            return countX - countY;
        }

        if (a instanceof Double && b instanceof Double){
            double mantysaX = getMantysa((Double) a);
            double mantysaY = getMantysa((Double) b);

            return Double.compare(mantysaX, mantysaY);
        }

        return 0;

    }

    private int countDigits(Integer a){
        String text = a.toString();
        int count=0;
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            if (c != '0' && c != '-') {
                count++;
            }
        }
        return count;
    }

    private double getMantysa(Double a){
        if (a==0.0) return 0;
        double temp = Math.abs(a);

        while(temp >= 10.0)
            temp /= 10.0;

        while(temp <= 1.0)
            temp *= 10.0;

        return temp;
   
    }

    public void sort(T[] tab){
        int n = tab.length;
        for (int i=0; i<n-1; i++){
            for (int j=0; j<n-i-1; j++){
                if( myCompare(tab[j], tab[j+1]) > 0){
                    T temp = tab[j];
                    tab[j] = tab[j+1];
                    tab[j+1] = temp;
                }
            }
        }
    }


    public static void main (String[] args){
        String[] strings = {"Ala", "Zuza", "Piotr", "Kasia"};
        Integer[] integers = {1, 5, 8, 3, 6, 9};
        Double[] doubles = {5.6, 7.8, 3.4, 2.0, 9.6, 4.9};

        MyBubble<String> sortedStrings = new MyBubble<>();
        MyBubble<Integer> sortedIntegers = new MyBubble<>();
        MyBubble<Double> sortedDoubles = new MyBubble<>();

        sortedStrings.sort(strings);
        sortedIntegers.sort(integers);
        sortedDoubles.sort(doubles);

        System.out.println("Posortowane stringi: " + Arrays.toString(strings));
        System.out.println("Posortowane integery: " + Arrays.toString(integers));
        System.out.println("Posortowane double: " + Arrays.toString(doubles));
        
    }


}