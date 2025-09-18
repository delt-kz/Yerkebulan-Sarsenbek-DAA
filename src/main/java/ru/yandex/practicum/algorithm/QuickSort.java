package ru.yandex.practicum.algorithm;


import ru.yandex.practicum.util.Metrics;
import ru.yandex.practicum.util.ArrayUtils;

import java.util.Random;

public class QuickSort {
    private final Metrics metrics;
    private final Random random;

    public QuickSort(Metrics metrics) {
        this.metrics = metrics;
        this.random = new Random();
    }

    public void sort(int[] array) {
        if (array == null || array.length <= 1) return;
        sort(array, 0, array.length - 1);
    }

    private void sort(int[] array, int left, int right) {
        metrics.recordRecursionDepth();

        while (left < right) {
            if (right - left < 16) {
                insertionSort(array, left, right);
                return;
            }

            int pivotIndex = partition(array, left, right);

            // Recurse on smaller partition, iterate on larger
            if (pivotIndex - left < right - pivotIndex) {
                sort(array, left, pivotIndex - 1);
                left = pivotIndex + 1;
            } else {
                sort(array, pivotIndex + 1, right);
                right = pivotIndex - 1;
            }
        }
    }

    private int partition(int[] array, int left, int right) {
        int randomIndex = left + random.nextInt(right - left + 1);
        ArrayUtils.swap(array, randomIndex, right);

        int pivot = array[right];
        int i = left - 1;

        for (int j = left; j < right; j++) {
            metrics.incrementComparisons(1);
            if (array[j] <= pivot) {
                i++;
                ArrayUtils.swap(array, i, j);
            }
        }

        ArrayUtils.swap(array, i + 1, right);
        return i + 1;
    }

    private void insertionSort(int[] array, int left, int right) {
        for (int i = left + 1; i <= right; i++) {
            int key = array[i];
            int j = i - 1;

            while (j >= left && array[j] > key) {
                metrics.incrementComparisons(1);
                array[j + 1] = array[j];
                j--;
            }
            array[j + 1] = key;
        }
    }
}