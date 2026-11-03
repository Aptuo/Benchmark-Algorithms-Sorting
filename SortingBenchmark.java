import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;

public class sortingbenchmark {

    private static final int SIZE = 100_000;

    static class Algorithm {
        final String name;
        final Consumer<int[]> sort;

        Algorithm(String name, Consumer<int[]> sort) {
            this.name = name;
            this.sort = sort;
        }
    }

    public static void main(String[] args) {
        int[][] scenarioArrays = {
            Scenarios.sortedArray(),
            Scenarios.reverseArray(),
            Scenarios.partialArray(),
            Scenarios.randomArray()
        };

        String[] scenarioNames = {
            "Sorted", "Reverse", "Partial", "Random"
        };

        List<Algorithm> algorithms = Arrays.asList(
            new Algorithm("Selection Sort", SortingAlgorithms::selectionSort),
            new Algorithm("Insertion Sort", SortingAlgorithms::insertionSort),
            new Algorithm("Bubble Sort", SortingAlgorithms::bubbleSort),
            new Algorithm("Merge Sort", SortingAlgorithms::mergeSort),
            new Algorithm("Quick Sort", SortingAlgorithms::quickSort)
        );

        System.out.printf("%-18s %-12s %15s%n",
                "Algorithm", "Scenario", "Time (microseconds)");

        for (Algorithm algorithm : algorithms) {
            for (int i = 0; i < scenarioArrays.length; i++) {
                // Give every algorithm the same original input
                int[] testArray = scenarioArrays[i].clone();

                long start = System.nanoTime();
                algorithm.sort.accept(testArray);
                long elapsedMicros = (System.nanoTime() - start) / 1_000;

                // Check correctness outside the timed section
                if (!isSorted(testArray)) {
                    throw new IllegalStateException(
                            algorithm.name + " failed on " + scenarioNames[i]);
                }

                System.out.printf("%-18s %-12s %15d%n",
                        algorithm.name, scenarioNames[i], elapsedMicros);
            }
        }
    }

    private static boolean isSorted(int[] array) {
        for (int i = 1; i < array.length; i++) {
            if (array[i - 1] > array[i]) {
                return false;
            }
        }
        return true;
    }

    static class SortingAlgorithms {

        public static void selectionSort(int[] arr) {
            for (int i = 0; i < arr.length - 1; i++) {
                int minIndex = i;

                for (int j = i + 1; j < arr.length; j++) {
                    if (arr[j] < arr[minIndex]) {
                        minIndex = j;
                    }
                }

                int temp = arr[i];
                arr[i] = arr[minIndex];
                arr[minIndex] = temp;
            }
        }

        public static void insertionSort(int[] arr) {
            for (int i = 1; i < arr.length; i++) {
                for (int j = i; j > 0 && arr[j - 1] > arr[j]; j--) {
                    int temp = arr[j];
                    arr[j] = arr[j - 1];
                    arr[j - 1] = temp;
                }
            }
        }

        public static void bubbleSort(int[] arr) {
            for (int i = 0; i < arr.length - 1; i++) {
                boolean swapped = false;

                for (int j = 0; j < arr.length - 1 - i; j++) {
                    if (arr[j] > arr[j + 1]) {
                        int temp = arr[j];
                        arr[j] = arr[j + 1];
                        arr[j + 1] = temp;
                        swapped = true;
                    }
                }

                if (!swapped) {
                    break;
                }
            }
        }

        public static void mergeSort(int[] arr) {
            if (arr.length < 2) {
                return;
            }

            int[] temp = new int[arr.length];
            mergeSort(arr, temp, 0, arr.length - 1);
        }

        private static void mergeSort(
                int[] arr, int[] temp, int left, int right) {
            if (left >= right) {
                return;
            }

            int middle = left + (right - left) / 2;

            mergeSort(arr, temp, left, middle);
            mergeSort(arr, temp, middle + 1, right);
            merge(arr, temp, left, middle, right);
        }

        private static void merge(
                int[] arr, int[] temp, int left, int middle, int right) {
            int i = left;
            int j = middle + 1;
            int k = left;

            while (i <= middle && j <= right) {
                if (arr[i] <= arr[j]) {
                    temp[k++] = arr[i++];
                } else {
                    temp[k++] = arr[j++];
                }
            }

            while (i <= middle) {
                temp[k++] = arr[i++];
            }

            while (j <= right) {
                temp[k++] = arr[j++];
            }

            for (k = left; k <= right; k++) {
                arr[k] = temp[k];
            }
        }

        public static void quickSort(int[] arr) {
            if (arr.length > 1) {
                quickSort(arr, 0, arr.length - 1);
            }
        }

        private static void quickSort(int[] arr, int low, int high) {
            while (low < high) {
                int pivot = arr[ThreadLocalRandom.current()
                        .nextInt(low, high + 1)];

                // Partition into values less than, equal to, and greater
                // than the pivot.
                int smaller = low;
                int current = low;
                int larger = high;

                while (current <= larger) {
                    if (arr[current] < pivot) {
                        swap(arr, smaller++, current++);
                    } else if (arr[current] > pivot) {
                        swap(arr, current, larger--);
                    } else {
                        current++;
                    }
                }

                // Recurse into the smaller side; loop over the larger side.
                if (smaller - low < high - larger) {
                    quickSort(arr, low, smaller - 1);
                    low = larger + 1;
                } else {
                    quickSort(arr, larger + 1, high);
                    high = smaller - 1;
                }
            }
        }

        private static void swap(int[] arr, int i, int j) {
            int temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;
        }
    }

    static class Scenarios {

        static int[] sortedArray() {
            int[] arr = new int[SIZE];

            for (int i = 0; i < arr.length; i++) {
                arr[i] = i;
            }

            return arr;
        }

        static int[] reverseArray() {
            int[] arr = new int[SIZE];

            for (int i = 0; i < arr.length; i++) {
                arr[i] = arr.length - 1 - i;
            }

            return arr;
        }

        static int[] randomArray() {
            int[] arr = new int[SIZE];
            Random random = new Random();

            for (int i = 0; i < arr.length; i++) {
                arr[i] = random.nextInt(SIZE + 1);
            }

            return arr;
        }

        static int[] partialArray() {
            int[] arr = new int[SIZE];
            Random random = new Random();
            int sortedPartLength = (int) (SIZE * 0.8);

            for (int i = 0; i < arr.length; i++) {
                if (i < sortedPartLength) {
                    arr[i] = i;
                } else {
                    arr[i] = random.nextInt(SIZE + 1);
                }
            }

            return arr;
        }
    }
}
