import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Consumer;

public class sortingbenchmark {

    // Benchmark size: large enough to reveal performance differences while keeping execution practical.
    // حجم المقياس: كبير بما يكفي لإظهار اختلافات الأداء مع الحفاظ على تنفيذ البرنامج عمليًا.
    private static final int SIZE = 100_000;

    // Represents one algorithm to benchmark along with the method that performs the sort.
    // يمثل خوارزمية واحدة للمقارنة مع الطريقة التي تؤدي الفرز.
    static class Algorithm {
        final String name;
        final Consumer<int[]> sort;

        Algorithm(String name, Consumer<int[]> sort) {
            this.name = name;
            this.sort = sort;
        }
    }

    public static void main(String[] args) {
        // Each scenario represents a different type of input data that a sorting algorithm may receive.
        // كل سيناريو يمثل نوعًا مختلفًا من بيانات الإدخال التي قد تتلقاها خوارزمية الفرز.
        int[][] scenarioArrays = {
            Scenarios.sortedArray(),
            Scenarios.reverseArray(),
            Scenarios.partialArray(),
            Scenarios.randomArray()
        };

        // Labels used when printing the results for each test case.
        // التسميات المستخدمة عند طباعة نتائج كل حالة اختبار.
        String[] scenarioNames = {
            "Sorted", "Reverse", "Partial", "Random"
        };

        // A list of algorithms under test. Each one is assigned a concrete sorting implementation.
        // قائمة بالخوارزميات قيد الاختبار. كل خوارزمية يتم تعيين لها تنفيذ فرز محدد.
        List<Algorithm> algorithms = Arrays.asList(
            new Algorithm("Selection Sort", SortingAlgorithms::selectionSort),
            new Algorithm("Insertion Sort", SortingAlgorithms::insertionSort),
            new Algorithm("Bubble Sort", SortingAlgorithms::bubbleSort),
            new Algorithm("Merge Sort", SortingAlgorithms::mergeSort),
            new Algorithm("Quick Sort", SortingAlgorithms::quickSort)
        );

        // Header for the benchmark output.
        // عنوان مخرجات المقارنة.
        System.out.printf("%-18s %-12s %15s%n",
                "Algorithm", "Scenario", "Time (microseconds)");

        // Measure each algorithm against every data scenario.
        // قياس كل خوارزمية مقابل كل سيناريو من بيانات الاختبار.
        for (Algorithm algorithm : algorithms) {
            for (int i = 0; i < scenarioArrays.length; i++) {
                // Give every algorithm the same original input by cloning the scenario before measuring.
                // نعطي كل خوارزمية نفس بيانات الإدخال الأصلية عن طريق نسخ السيناريو قبل القياس.
                int[] testArray = scenarioArrays[i].clone();

                long start = System.nanoTime();
                algorithm.sort.accept(testArray);

                // Convert nanoseconds to microseconds for the final report.
                // تحويل_nanoseconds إلى microseconds للتقرير النهائي.
                long elapsedMicros = (System.nanoTime() - start) / 1_000;

                // Check correctness outside the timed section so the benchmark only measures sorting speed.
                // التحقق من صحة النتيجة خارج فترة القياس حتى يقيس المقياس فقط سرعة الفرز.
                if (!isSorted(testArray)) {
                    throw new IllegalStateException(
                            algorithm.name + " failed on " + scenarioNames[i]);
                }

                System.out.printf("%-18s %-12s %15d%n",
                        algorithm.name, scenarioNames[i], elapsedMicros);
            }
        }
    }

    // Verifies that the array is in ascending order.
    // يتحقق من أن المصفوفة مرتبة بترتيب تصاعدي.
    private static boolean isSorted(int[] array) {
        for (int i = 1; i < array.length; i++) {
            if (array[i - 1] > array[i]) {
                return false;
            }
        }
        return true;
    }

    static class SortingAlgorithms {

        // Selection sort repeatedly finds the smallest remaining element and moves it to the front.
        // فرز الاختيار يكتشف العنصر الأصغر المتبقي بشكل متكرر وينقله إلى الأمام.
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

        // Insertion sort keeps the left side sorted and slides each new value into place.
        // فرز الإدراج يحتفظ بالجزء الأيسر مرتبًا ويُدرج كل قيمة جديدة في مكانها المناسب.
        public static void insertionSort(int[] arr) {
            for (int i = 1; i < arr.length; i++) {
                for (int j = i; j > 0 && arr[j - 1] > arr[j]; j--) {
                    int temp = arr[j];
                    arr[j] = arr[j - 1];
                    arr[j - 1] = temp;
                }
            }
        }

        // Bubble sort repeatedly swaps adjacent out-of-order values until no swaps are needed.
        // فرز الفقاعة يبادل القيم المتجاورة غير المرتبة بشكل متكرر حتى لا تكون هناك حاجة إلى تبديل.
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

                // If no values were swapped in this pass, the array is already sorted.
                // إذا لم يحدث أي تبادل في هذه الدورة، فهذا يعني أن المصفوفة أصبحت مرتبة بالفعل.
                if (!swapped) {
                    break;
                }
            }
        }

        // Merge sort divides the array into halves, sorts each half, then merges the results.
        // فرز الدمج يقسم المصفوفة إلى نصفين، ويقوم بفرز كل نصف، ثم يدمج النتائج.
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

        // Quick sort picks a pivot, partitions around it, and recursively sorts the smaller partitions.
        // فرز سريع يختار نقطة Pivot ثم يقسم المصفوفة حولها ويُفرز الأجزاء الأصغر بشكل متكرر.
        public static void quickSort(int[] arr) {
            if (arr.length > 1) {
                quickSort(arr, 0, arr.length - 1);
            }
        }

        private static void quickSort(int[] arr, int low, int high) {
            while (low < high) {
                // Choose a random pivot so the algorithm avoids worst-case behavior on already sorted input.
                // نختار Pivot بشكل عشوائي لتجنب أسوأ الحالات عند إدخال بيانات مرتبة بالفعل.
                int pivot = arr[ThreadLocalRandom.current()
                        .nextInt(low, high + 1)];

                // Partition into values less than, equal to, and greater than the pivot.
                // تقسيم المصفوفة إلى قيم أصغر من Pivot، أو تساوي Pivot، أو أكبر من Pivot.
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

                // Recurse into the smaller side; loop over the larger side to reduce stack depth.
                // نعيد استدعاء الدالة على الجانب الأصغر، ونستخدم الحلقة على الجانب الأكبر لتقليل عمق المكدس.
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

        // Returns a sorted array: best-case input for many algorithms.
        // يعيد مصفوفة مرتبة: وهي أفضل حالة إدخال للعديد من الخوارزميات.
        static int[] sortedArray() {
            int[] arr = new int[SIZE];

            for (int i = 0; i < arr.length; i++) {
                arr[i] = i;
            }

            return arr;
        }

        // Returns a descending array: worst-case input for many simple sorting algorithms.
        // يعيد مصفوفة مرتبة ترتيبًا تنازليًا: وهي أسوأ حالة إدخال للعديد من الخوارزميات البسيطة.
        static int[] reverseArray() {
            int[] arr = new int[SIZE];

            for (int i = 0; i < arr.length; i++) {
                arr[i] = arr.length - 1 - i;
            }

            return arr;
        }

        // Creates a random array to simulate typical unpredictable input data.
        // ينشئ مصفوفة عشوائية لمحاكاة بيانات الإدخال غير المتوقعة في الحياة العملية.
        static int[] randomArray() {
            int[] arr = new int[SIZE];
            Random random = new Random();

            for (int i = 0; i < arr.length; i++) {
                arr[i] = random.nextInt(SIZE + 1);
            }

            return arr;
        }

        // Produces an array that is mostly sorted but has a noisy tail, which is common in real-world data.
        // ينتج مصفوفة مرتبة إلى حد كبير ولكن مع ذيل غير منظم، وهو أمر شائع في البيانات الواقعية.
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
