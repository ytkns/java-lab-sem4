import java.util.Random;

class PartialSumThread extends Thread {
    private double[] array;
    private int start, end;
    private double partialSum = 0;

    public PartialSumThread(double[] array, int start, int end) {
        this.array = array;
        this.start = start;
        this.end = end;
    }

    @Override
    public void run() {
        for (int i = start; i < end; i++) {
            partialSum += array[i];
        }
    }

    public double getPartialSum() {
        return partialSum;
    }
}

class PartialSumRunnable implements Runnable {
    private double[] array;
    private int start, end;
    private double partialSum = 0;

    public PartialSumRunnable(double[] array, int start, int end) {
        this.array = array;
        this.start = start;
        this.end = end;
    }

    @Override
    public void run() {
        for (int i = start; i < end; i++)
            partialSum += array[i];
    }

    public double getPartialSum() {
        return partialSum;
    }
}

public class Zad1 {
    public static void main(String[] args) throws InterruptedException {
        int N = 1000000;
        int K = 4; 
        double[] array = new double[N];
        Random rand = new Random();

        for (int i = 0; i < N; i++) {
            array[i] = rand.nextDouble() * 100;
        }
        
        // Sekwencyjnie
        long startTime = System.nanoTime();
        double sequentialSum = 0;
        for (int i = 0; i < N; i++) {
            sequentialSum += array[i];
        }
        long endTime = System.nanoTime();
        System.out.printf("Suma (sekwencyjna): %.2f | Czas: %.2f ms\n", sequentialSum, (endTime - startTime) / 1e6);

        // Wielowątkowo, dziedziczenie po Thread
        startTime = System.nanoTime();
        PartialSumThread[] threads = new PartialSumThread[K];
        int m = N / K;

        for (int i = 0; i < K; i++) {
            int start = i * m;
            int end = (i == K - 1) ? N : (i + 1) * m;
            threads[i] = new PartialSumThread(array, start, end);
            threads[i].start();
        }

        double threadSum = 0;
        for (int i = 0; i < K; i++) {
            threads[i].join(); 
            threadSum += threads[i].getPartialSum();
        }
        endTime = System.nanoTime();
        System.out.printf("Suma (Thread):      %.2f | Czas: %.2f ms\n", threadSum, (endTime - startTime) / 1e6);

        // Wielowatkowo, implementajca Runnable
        startTime = System.nanoTime();
        PartialSumRunnable[] runnables = new PartialSumRunnable[K];
        Thread[] runnableThreads = new Thread[K];

        for (int i = 0; i < K; i++) {
            int start = i * m;
            int end = (i == K - 1) ? N : (i + 1) * m;
            runnables[i] = new PartialSumRunnable(array, start, end);
            runnableThreads[i] = new Thread(runnables[i]);
            runnableThreads[i].start();
        }

        double runnableSum = 0;
        for (int i = 0; i < K; i++) {
            runnableThreads[i].join(); 
            runnableSum += runnables[i].getPartialSum();
        }
        endTime = System.nanoTime();
        System.out.printf("Suma (Runnable):    %.2f | Czas: %.2f ms\n", runnableSum, (endTime - startTime) / 1e6);
    }
}