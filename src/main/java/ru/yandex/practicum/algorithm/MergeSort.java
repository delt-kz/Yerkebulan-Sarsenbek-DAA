package ru.yandex.practicum.algorithm;


import ru.yandex.practicum.util.Metrics;


public class MergeSort {
    private static final int INSERTION_CUTOFF = 16;
    private final Metrics metrics;

    public MergeSort(Metrics metrics) {
        this.metrics = metrics;
    }

    public void sort(int[] array) {
        if (array == null || array.length <= 1) return;
        int[] buffer = new int[array.length];
        sort(array, 0, array.length - 1, buffer);
    }

    private void sort(int[] array, int left, int right, int[] buffer) {
        metrics.enterRecursion();
        try {
            if (right - left + 1 <= INSERTION_CUTOFF) {
                insertionSort(array, left, right);
                return;
            }

            int mid = left + (right - left) / 2;
            sort(array, left, mid, buffer);
            sort(array, mid + 1, right, buffer);
            merge(array, left, mid, right, buffer);
        } finally {
            metrics.exitRecursion();
        }
    }
    private void merge(int[] array, int left, int mid, int right, int[] buffer) {
        metrics.incrementComparisons(right - left);
        metrics.incrementAllocations(right - left + 1);

        System.arraycopy(array, left, buffer, left, right - left + 1);

        int i = left, j = mid + 1, k = left;
        while (i <= mid && j <= right) {
            metrics.incrementComparisons(1);
            if (buffer[i] <= buffer[j]) {
                array[k++] = buffer[i++];
            } else {
                array[k++] = buffer[j++];
            }
        }

        while (i <= mid) {
            array[k++] = buffer[i++];
        }
        while (j <= right) {
            array[k++] = buffer[j++];
        }
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