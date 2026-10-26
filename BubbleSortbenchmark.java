import java.util.concurrent.ThreadLocalRandom;

public class test {

    // Entry point of the program.
    public static void main(String[] args) {
        // Create two arrays of 100,000 integers.
        // One will store random values, while the other will store values in reverse order.
        int[] arrayRandom = new int[100_000];
        int[] arrayReverse = new int[100_000];

        // Start from 100000 so the reverse array is filled in descending order.
        int j = 100000;

        // Fill both arrays with values.
        for (int i = 0; i < arrayRandom.length; i++) {
            // Generate a random integer from 0 to 100,000.
            arrayRandom[i] = ThreadLocalRandom.current().nextInt(100_001);

            // Fill the reverse-sorted array with values from 100000 down to 1.
            arrayReverse[i] = j--;
        }

        // Run the bubble sort on the random array and record its time.
        System.out.println("RandomArray");
        long RandomArraytime = bubblesort(arrayRandom);

        // Run the bubble sort on the reverse-sorted array and record its time.
        System.out.println("ReverseArray");
        long ReverseArrayTime = bubblesort(arrayReverse);

        // Compare the sorting times and print which case was faster.
        if (RandomArraytime > ReverseArrayTime) {
            System.out.println("Reverse was faster by " + (RandomArraytime - ReverseArrayTime) / 1_000.0 + " microSeconds");
        } else if (ReverseArrayTime > RandomArraytime) {
            System.out.println("Random was faster by " + (ReverseArrayTime - RandomArraytime) / 1_000.0 + " microSeconds");
        } else {
            System.out.println("both scenarios took the same time");
        }
    }

    // Bubble sort algorithm.
    // It compares adjacent values and swaps them until the array is sorted.
    public static long bubblesort(int arr[]) {
        // Store the start time before sorting begins.
        long start = System.nanoTime();

        // Repeatedly pass through the array.
        for (int i = 0; i < arr.length - 1; i++) {
            // Compare each adjacent pair in the current pass.
            for (int j = 0; j < arr.length - i - 1; j++) {
                // If the left value is greater than the right value, swap them.
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }

        // Compute the difference between the end time and the start time.
        long elapsed = System.nanoTime() - start;

        // Print the execution time in different units for comparison.
        System.out.println("Time: " + elapsed + " nanoseconds");
        System.out.println("Time: " + elapsed / 1_000_000.0 + " milliseconds");
        System.out.println("Time: " + elapsed / 1_000.0 + " microseconds");

        // Return the elapsed time so it can be compared with the other array.
        return elapsed;
    }
}
