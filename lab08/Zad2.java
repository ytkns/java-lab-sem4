import java.util.Scanner;

class SharedArray {
    public int[] array;
    public boolean hasData = false; 
    public int n;

    public SharedArray(int n) {
        this.n = n;
        this.array = new int[n];
    }
}

class GetNumbers extends Thread {
    private SharedArray sharedArray;
    private int iterations;

    public GetNumbers(SharedArray sharedArray, int iterations) {
        this.sharedArray = sharedArray;
        this.iterations = iterations; 
    }

    @Override
    public void run() {
        Scanner scanner = new Scanner(System.in);

        for (int k = 0; k < iterations; k++) {
            synchronized (sharedArray) { 
 
                while (sharedArray.hasData) {
                    try {
                        System.out.println("[GetNumbers] Czekam aż poprzednia suma zostanie obliczona");
                        sharedArray.wait(); 
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }

                System.out.println("\n[GetNumbers] Podaj " + sharedArray.n + " liczb całkowitych:");
                for (int i = 0; i < sharedArray.n; i++) {
                    System.out.print("Liczba " + (i + 1) + ": ");
                    sharedArray.array[i] = scanner.nextInt();
                }

                sharedArray.hasData = true;
                sharedArray.notifyAll(); 
            }
        }
    }
}

class GetSum extends Thread {
    private SharedArray sharedArray;
    private int iterations;

    public GetSum(SharedArray sharedArray, int iterations) {
        this.sharedArray = sharedArray;
        this.iterations = iterations;
    }

    @Override
    public void run() {
        for (int k = 0; k < iterations; k++) {
            synchronized (sharedArray) {
                while (!sharedArray.hasData) {
                    try {
                        sharedArray.wait(); 
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }

                int sum = 0;
                for (int i = 0; i < sharedArray.n; i++) {
                    sum += sharedArray.array[i];
                }
                System.out.println("[GetSum] Suma wynosi: " + sum);
                
                for (int i = 0; i < sharedArray.n; i++) {
                    sharedArray.array[i] = 0;
                }
                System.out.println("[GetSum] Tablica wyzerowana.");

                try {
                    System.out.println("[GetSum] Sztuczne opóźnienie (2 sekundy)...");
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }

                sharedArray.hasData = false;
                sharedArray.notifyAll(); 
            }
        }
    }
}

public class Zad2 {
    public static void main(String[] args) {
        int N = 5; 
        int testIterations = 2;

        SharedArray sharedArray = new SharedArray(N);

        GetNumbers threadGetNumbers = new GetNumbers(sharedArray, testIterations);
        GetSum threadGetSum = new GetSum(sharedArray, testIterations);

        threadGetSum.start();
        threadGetNumbers.start();

        try {
            threadGetSum.join(); 
            threadGetNumbers.join();
        } catch (InterruptedException e) {
            System.out.println("Wątek główny przerwany");
        }
        System.out.println("\nKoniec działania");
    }
}