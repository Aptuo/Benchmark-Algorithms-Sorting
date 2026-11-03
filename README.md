# Benchmark-Algorithms-Sorting
Name : عبد المعين فرج المرامي
Student ID : 3153018

This project benchmarks several sorting algorithms using different input scenarios to compare their performance and verify correctness.

English: The program creates sorted, reverse-sorted, partially sorted, and random arrays, then measures how long each sorting algorithm takes to process them.
Arabic: ينشئ هذا البرنامج مصفوفات مرتبة ومرتبة ترتيباً عكسياً ومرتبة جزئياً وعشوائية، ثم يقيس الوقت الذي تستغرقه كل خوارزمية فرز لمعالجة تلك البيانات.

## Benchmark results

Results from the latest run (time in microseconds):

| Algorithm | Sorted | Reverse | Partial | Random |
|---|---:|---:|---:|---:|
| Selection Sort | 1,417,768 | 5,108,022 | 2,534,388 | 2,523,922 |
| Insertion Sort | 1,888 | 6,681,888 | 545,433 | 1,779,466 |
| Bubble Sort | 1,853 | 8,034,224 | 8,242,685 | 16,856,373 |
| Merge Sort | 16,430 | 55,391 | 7,767 | 14,543 |
| Quick Sort | 27,389 | 18,526 | 11,500 | 13,493 |

### Benchmark chart

The chart uses a logarithmic scale (`log10` of microseconds) so the much faster Sorted and Partial results remain visible alongside the slower results. Refer to the table above for exact runtimes.

```mermaid
xychart-beta
    title "Sorting Algorithm Benchmark (log10 microseconds)"
    x-axis ["Selection", "Insertion", "Bubble", "Merge", "Quick"]
    y-axis "log10(Time in microseconds)" 3 --> 8
    bar "Sorted" [6.15, 3.28, 3.27, 4.22, 4.44]
    bar "Reverse" [6.71, 6.82, 6.91, 4.74, 4.27]
    bar "Partial" [6.40, 5.74, 6.92, 3.89, 4.06]
    bar "Random" [6.40, 6.25, 7.23, 4.16, 4.13]
```

Note: Timing results depend on the machine and runtime environment, so repeated runs may produce different values.

Each algorithm is tested against the same four input scenarios. The benchmark copies each original array before sorting and checks that the result is in ascending order.

يتم اختبار كل خوارزمية باستخدام سيناريوهات الإدخال الأربعة نفسها. تنسخ المقارنة كل مصفوفة أصلية قبل الفرز، ثم تتحقق من أن النتيجة مرتبة تصاعدياً.

## Sorting algorithms included

- Selection Sort
- Insertion Sort
- Bubble Sort
- Merge Sort
- Quick Sort
