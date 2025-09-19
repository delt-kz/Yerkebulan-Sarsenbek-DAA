package ru.yandex.practicum.algorithm;


import ru.yandex.practicum.util.ArrayUtils;
import ru.yandex.practicum.util.Metrics;

import java.util.Arrays;

public class DeterministicSelect {
    private final Metrics metrics;
    private static final int GROUP_SIZE = 5;

    public DeterministicSelect(Metrics metrics) {
        this.metrics = metrics;
    }

    public int select(int[] array, int k) {
        if (array == null || array.length == 0) {
            throw new IllegalArgumentException("Array cannot be null or empty");
        }
        if (k < 0 || k >= array.length) {
            throw new IllegalArgumentException("k must be between 0 and array length - 1");
        }

        int[] copy = array.clone();
        return select(copy, 0, copy.length - 1, k);
    }

    private int select(int[] array, int left, int right, int k) {
        metrics.enterRecursion();
        try {
            if (left == right) {
                return array[left];
            }

            int pivotIndex = medianOfMedians(array, left, right);
            pivotIndex = partition(array, left, right, pivotIndex);

            if (k == pivotIndex) {
                return array[k];
            } else if (k < pivotIndex) {
                return select(array, left, pivotIndex - 1, k);
            } else {
                return select(array, pivotIndex + 1, right, k);
            }
        } finally {
            metrics.exitRecursion();
        }
    }

    private int medianOfMedians(int[] array, int left, int right) {
        int n = right - left + 1;
        int numGroups = (int) Math.ceil(n / (double) GROUP_SIZE);
        int[] medians = new int[numGroups];

        for (int i = 0; i < numGroups; i++) {
            int groupLeft = left + i * GROUP_SIZE;
            int groupRight = Math.min(groupLeft + GROUP_SIZE - 1, right);
            medians[i] = findMedian(array, groupLeft, groupRight);
        }

        if (medians.length == 1) {
            return findMedianIndex(array, left, right, medians[0]);
        } else {
            int medianOfMediansValue = select(medians, 0, medians.length - 1, medians.length / 2);
            return findMedianIndex(array, left, right, medianOfMediansValue);
        }
    }

    private int findMedian(int[] array, int left, int right) {
        int[] group = Arrays.copyOfRange(array, left, right + 1);
        Arrays.sort(group);
        return group[group.length / 2];
    }

    private int findMedianIndex(int[] array, int left, int right, int medianValue) {
        for (int i = left; i <= right; i++) {
            if (array[i] == medianValue) {
                return i;
            }
        }
        return left;
    }

    private int partition(int[] array, int left, int right, int pivotIndex) {
        ArrayUtils.swap(array, pivotIndex, right);
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
}