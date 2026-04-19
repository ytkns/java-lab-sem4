import java.util.Arrays;

class SortBubble{
    public static <T extends Comparable<T>> void sort(T[] tab){
        int n = tab.length;
        for (int i=0; i<n-1; i++){
            for (int j=0; j<n-i-1; j++){
                if( tab[j].compareTo(tab[j+1]) > 0){
                    T temp = tab[j];
                    tab[j] = tab[j+1];
                    tab[j+1] = temp;
                }
            }
        }
    }

    public static void main(String[] args){
        Integer[] integers = {1, 5, 8, 3, 6, 9};
        Double[] doubles = {5.6, 7.8, 3.4, 2.0, 9.6, 4.9};
        Long[] longs = {200L, 500L, 300L, 100L, 400L};

        SortBubble.sort(integers);
        SortBubble.sort(doubles);
        SortBubble.sort(longs);

        System.out.println("Posortowane integery: " + Arrays.toString(integers));
        System.out.println("Posortowane double: " + Arrays.toString(doubles));
        System.out.println("Posortowane longi: " + Arrays.toString(longs));

    }
}