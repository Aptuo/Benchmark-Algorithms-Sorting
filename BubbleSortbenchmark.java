import java.util.concurrent.ThreadLocalRandom;

public class BubbleSortbenchmark {

    // Entry point of the program.
    // نقطة الدخول في البرنامج.
    public static void main(String[] args) {
        // Create two arrays of 100,000 integers.
        // أنشئ مصفوفتين مكونتين من 100000 عدد صحيح.
        int[] arrayRandom = new int[100_000];
        int[] arrayReverse = new int[100_000];

        // Start from 100000 so the reverse array is filled in descending order.
        // ابدأ من 100000 حتى يتم ملء المصفوفة العكسية بترتيب تنازلي.
        int j = 100000;

        // Fill both arrays with values.
        // املأ كلتا المصفوفتين بالقيم.
        for (int i = 0; i < arrayRandom.length; i++) {
            // Generate a random integer from 0 to 100,000.
            // أنشئ عددًا صحيحًا عشوائيًا من 0 إلى 100000.
            arrayRandom[i] = ThreadLocalRandom.current().nextInt(100_001);

            // Fill the reverse-sorted array with values from 100000 down to 1.
            // املأ المصفوفة العكسية بالقيم من 100000 إلى 1.
            arrayReverse[i] = j--;
        }

        // Run the bubble sort on the random array and record its time.
        // نفّذ فرز الفقاعات على المصفوفة العشوائية وسجل الوقت.
        System.out.println("RandomArray");
        long randomArrayTimeNs = bubbleSort(arrayRandom);

        // Run the bubble sort on the reverse-sorted array and record its time.
        // نفّذ فرز الفقاعات على المصفوفة المرتبة عكسيًا وسجل الوقت.
        System.out.println("ReverseArray");
        long reverseArrayTimeNs = bubbleSort(arrayReverse);

        double randomMicroseconds = nanosToMicroseconds(randomArrayTimeNs);
        double reverseMicroseconds = nanosToMicroseconds(reverseArrayTimeNs);

        System.out.printf("Random array time: %.3f microseconds%n", randomMicroseconds);
        System.out.printf("Reverse array time: %.3f microseconds%n", reverseMicroseconds);

        // Compare the sorting times and print which case was faster.
        // قارن أوقات الفرز واطبع أي حالة كانت أسرع.
        if (randomArrayTimeNs > reverseArrayTimeNs) {
            System.out.printf("Reverse was faster by %.3f microseconds%n", randomMicroseconds - reverseMicroseconds);
        } else if (reverseArrayTimeNs > randomArrayTimeNs) {
            System.out.printf("Random was faster by %.3f microseconds%n", reverseMicroseconds - randomMicroseconds);
        } else {
            System.out.println("Both scenarios took the same time");
        }
    }

    // Bubble sort algorithm.
    // خوارزمية فرز الفقاعات.
    // It compares adjacent values and swaps them until the array is sorted.
    // تقارن القيم المتجاورة وتبدلها حتى تصبح المصفوفة مرتبة.
    public static long bubbleSort(int[] arr) {
        // Store the start time before sorting begins.
        // خزّن وقت البداية قبل بدء الفرز.
        long start = System.nanoTime();

        // Repeatedly pass through the array.
        // نمرّر عبر المصفوفة بشكل متكرر.
        for (int i = 0; i < arr.length - 1; i++) {
            // Compare each adjacent pair in the current pass.
            // قارن كل زوج متجاور في المرور الحالي.
            for (int j = 0; j < arr.length - i - 1; j++) {
                // If the left value is greater than the right value, swap them.
                // إذا كانت القيمة الموجودة على اليسار أكبر من القيمة الموجودة على اليمين، فقم بتبديلهما.
                if (arr[j] > arr[j + 1]) {
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                }
            }
        }

        // Compute the difference between the end time and the start time.
        // احسب الفرق بين وقت النهاية ووقت البداية.
        long elapsed = System.nanoTime() - start;

        // Print the execution time in different units for comparison.
        // اطبع الوقت المنقضي بوحدات مختلفة للمقارنة.
        System.out.printf("Time: %.3f nanoseconds%n", (double) elapsed);
        System.out.printf("Time: %.3f milliseconds%n", elapsed / 1_000_000.0);
        System.out.printf("Time: %.3f microseconds%n", nanosToMicroseconds(elapsed));

        // Return the elapsed time so it can be compared with the other array.
        // ارجع الوقت المنقضي حتى يمكن مقارنته مع المصفوفة الأخرى.
        return elapsed;
    }

    private static double nanosToMicroseconds(long nanos) {
        return nanos / 1_000.0;
    }
}
