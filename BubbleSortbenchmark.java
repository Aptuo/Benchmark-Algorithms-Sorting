
import java.util.concurrent.ThreadLocalRandom;

public class test {

    public static void main(String[] args) {
        int[] arrayRandom = new int[100_000];
        int[] arrayReverse = new int[100_000];
        int j = 100000;
        for (int i = 0; i < arrayRandom.length; i++) {
            arrayRandom[i] = ThreadLocalRandom.current().nextInt(100_001);
            arrayReverse[i] = j--;

        }

        System.out.println("RandomArray");
        long RandomArraytime = bubblesort(arrayRandom);
        System.out.println("ReverseArray");
        long ReverseArrayTime = bubblesort(arrayReverse);

        if (RandomArraytime > ReverseArrayTime) {
            System.out.println("Reverse was faster by " + (RandomArraytime - ReverseArrayTime) / 1_000.0 + " microSeconds");
        } else if (ReverseArrayTime > RandomArraytime) {
            System.out.println("Random was faster by " + (ReverseArrayTime - RandomArraytime) / 1_000.0 + " microSeconds");
        } else {
            System.out.println("both scenarios took the same time");
        }

    }

    public static long bubblesort(int arr[]) {
        long start = System.nanoTime();

        for (int i = 0; i < arr.length - 1; i++) {
            for (int j = 0; j < arr.length - i - 1; j++) {
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }
        long elapsed = System.nanoTime() - start;

        System.out.println("Time: " + elapsed + " nanoseconds");
        System.out.println("Time: " + elapsed / 1_000_000.0 + " milliseconds");
        System.out.println("Time: " + elapsed / 1_000.0 + " microseconds");
        return elapsed;
    }
}
