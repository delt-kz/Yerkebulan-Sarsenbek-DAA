package ru.yandex.practicum.algorithm;


public class MergeSort {
    private static final int INSERTION_CUTOFF = 16;


    public void sort(int[] array) {
        if (array == null || array.length <= 1) return;
        int[] buffer = new int[array.length];
        sort(array, 0, array.length - 1, buffer);
    }

    private void sort(int[] array, int left, int right, int[] buffer) {

        if (right - left + 1 <= INSERTION_CUTOFF) {
            insertionSort(array, left, right);
            return;
        }

        int mid = left + (right - left) / 2;
        sort(array, left, mid, buffer);
        sort(array, mid + 1, right, buffer);
        merge(array, left, mid, right, buffer);
    }

    private void merge(int[] array, int left, int mid, int right, int[] buffer) {

        System.arraycopy(array, left, buffer, left, right - left + 1);

        int i = left, j = mid + 1, k = left;
        while (i <= mid && j <= right) {
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
                array[j + 1] = array[j];
                j--;
            }
            array[j + 1] = key;
        }
    }
}