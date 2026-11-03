# Benchmark-Algorithms-Sorting
Name : عبد المعين فرج المرامي
Student ID : 3153018

This project benchmarks several sorting algorithms using different input scenarios to compare their performance and verify correctness.

 The program creates sorted, reverse-sorted, partially sorted, and random arrays, then measures how long each sorting algorithm takes to process them.
 
  ينشئ هذا البرنامج مصفوفات مرتبة ومرتبة ترتيباً عكسياً ومرتبة جزئياً وعشوائية، ثم يقيس الوقت الذي تستغرقه كل خوارزمية فرز لمعالجة تلك البيانات.

## Benchmark results

Results from the latest run. All times are in microseconds (µs):

| Algorithm | Sorted (µs) | Reverse (µs) | Partial (µs) | Random (µs) |
|---|---:|---:|---:|---:|
| Selection Sort | 1,417,768 | 5,108,022 | 2,534,388 | 2,523,922 |
| Insertion Sort | 1,888 | 6,681,888 | 545,433 | 1,779,466 |
| Bubble Sort | 1,853 | 8,034,224 | 8,242,685 | 16,856,373 |
| Merge Sort | 16,430 | 55,391 | 7,767 | 14,543 |
| Quick Sort | 27,389 | 18,526 | 11,500 | 13,493 |

## Separate benchmark charts

Each algorithm has its own chart with a linear scale in microseconds (µs). Separate scales make the smaller values easier to see without transforming the reported timings.

### Selection Sort

```mermaid
xychart-beta
    title "Selection Sort (µs)"
    x-axis ["Sorted", "Reverse", "Partial", "Random"]
    y-axis "Time (µs)" 0 --> 6000000
    bar [1417768, 5108022, 2534388, 2523922]
```

### Insertion Sort

```mermaid
xychart-beta
    title "Insertion Sort (µs)"
    x-axis ["Sorted", "Reverse", "Partial", "Random"]
    y-axis "Time (µs)" 0 --> 7000000
    bar [1888, 6681888, 545433, 1779466]
```

### Bubble Sort

```mermaid
xychart-beta
    title "Bubble Sort (µs)"
    x-axis ["Sorted", "Reverse", "Partial", "Random"]
    y-axis "Time (µs)" 0 --> 18000000
    bar [1853, 8034224, 8242685, 16856373]
```

### Merge Sort

```mermaid
xychart-beta
    title "Merge Sort (µs)"
    x-axis ["Sorted", "Reverse", "Partial", "Random"]
    y-axis "Time (µs)" 0 --> 60000
    bar [16430, 55391, 7767, 14543]
```

### Quick Sort

```mermaid
xychart-beta
    title "Quick Sort (µs)"
    x-axis ["Sorted", "Reverse", "Partial", "Random"]
    y-axis "Time (µs)" 0 --> 30000
    bar [27389, 18526, 11500, 13493]
```

## Time complexity comparison

The Big-O notation below describes how the number of operations grows as the input size `N` increases. These are theoretical time complexities; actual times depend on the input, implementation, and runtime environment.

| Algorithm | Best case | Average case | Worst case | Explanation |
|---|---:|---:|---:|---|
| Selection Sort | O(N²) | O(N²) | O(N²) | Scans the remaining elements to find the minimum on every pass. |
| Insertion Sort | O(N) | O(N²) | O(N²) | Efficient when data is already sorted or nearly sorted; shifts elements to insert each value. |
| Bubble Sort | O(N) | O(N²) | O(N²) | With the early-exit check, it stops after a pass with no swaps; otherwise repeated adjacent comparisons dominate. |
| Merge Sort | O(N log N) | O(N log N) | O(N log N) | Divides the array into halves and merges the sorted halves; requires additional memory. |
| Quick Sort | O(N log N) | O(N log N) | O(N²) | Random pivot selection gives good expected performance, but highly unbalanced partitions can lead to quadratic time. |

### Interpreting the comparison

- Algorithms with **O(N log N)** average time complexity, such as Merge Sort and Quick Sort, generally scale better on large inputs than **O(N²)** algorithms.
- Insertion Sort is often a good choice for already sorted or nearly sorted data; its best-case complexity is **O(N)**. The specific partially sorted benchmark in this project may not be close enough to fully sorted for that advantage to dominate.
- Quick Sort's pivot affects partition balance. This implementation chooses a pivot randomly, reducing the likelihood of consistently poor partitions, but it does not eliminate the **O(N²)** worst case.
- These benchmark measurements are a single run and can vary by machine, JVM warm-up, and runtime conditions. Use repeated runs for a more reliable performance comparison.

### مقارنة التعقيد الزمني

توضح الرموز Big-O كيفية نمو عدد العمليات مع زيادة حجم الإدخال `N`. هذه تعقيدات نظرية، وقد يختلف الزمن الفعلي بحسب البيانات والتنفيذ وبيئة التشغيل.

- عادةً ما تتوسع خوارزميات **O(N log N)**، مثل Merge Sort وQuick Sort، بشكل أفضل مع المدخلات الكبيرة مقارنةً بخوارزميات **O(N²)**.
- تكون Insertion Sort مناسبة غالباً للبيانات المرتبة أو شبه المرتبة؛ وتعقيدها في أفضل حالة هو **O(N)**.
- يؤثر اختيار المحور (Pivot) في Quick Sort على توازن التقسيمات. يستخدم التنفيذ محوراً عشوائياً لتقليل احتمال التقسيمات السيئة المتكررة، لكنه لا يلغي أسوأ حالة **O(N²)**.
- هذه القياسات من تشغيل واحد وقد تختلف باختلاف الجهاز وإحماء JVM وظروف التشغيل. للحصول على مقارنة أكثر موثوقية، يُفضّل تكرار القياسات.

Note: Timing results depend on the machine and runtime environment, so repeated runs may produce different values.

Each algorithm is tested against the same four input scenarios. The benchmark copies each original array before sorting and checks that the result is in ascending order.

يتم اختبار كل خوارزمية باستخدام سيناريوهات الإدخال الأربعة نفسها. تنسخ المقارنة كل مصفوفة أصلية قبل الفرز، ثم تتحقق من أن النتيجة مرتبة تصاعدياً.

## Sorting algorithms included

- Selection Sort
- Insertion Sort
- Bubble Sort
- Merge Sort
- Quick Sort
