package ru.yandex.practicum;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.algorithm.MergeSort;
import ru.yandex.practicum.util.Metrics;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Random;

class MergeSortTest {
    private MergeSort mergeSort;
    private Metrics metrics;

    @BeforeEach
    void setUp() {
        metrics = new Metrics();
        mergeSort = new MergeSort(metrics);
    }

    @Test
    void testEmptyArray() {
        int[] array = {};
        mergeSort.sort(array);
        assertArrayEquals(new int[]{}, array);
    }

    @Test
    void testSingleElement() {
        int[] array = {5};
        mergeSort.sort(array);
        assertArrayEquals(new int[]{5}, array);
    }

    @Test
    void testSortedArray() {
        int[] array = {1, 2, 3, 4, 5};
        mergeSort.sort(array);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, array);
    }

    @Test
    void testReverseSortedArray() {
        int[] array = {5, 4, 3, 2, 1};
        mergeSort.sort(array);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, array);
    }

    @Test
    void testRandomArray() {
        int[] array = {3, 1, 4, 1, 5, 9, 2, 6};
        int[] expected = array.clone();
        Arrays.sort(expected);

        mergeSort.sort(array);
        assertArrayEquals(expected, array);
    }

    @Test
    void testAdversarialArray() {
        int[] array = {1, 3, 5, 7, 9, 2, 4, 6, 8, 10};
        int[] expected = array.clone();
        Arrays.sort(expected);

        mergeSort.sort(array);
        assertArrayEquals(expected, array);
    }

    @Test
    void testLargeRandomArray() {
        Random random = new Random(42);
        int[] array = random.ints(1000).toArray();
        int[] expected = array.clone();
        Arrays.sort(expected);

        metrics.reset();
        mergeSort.sort(array);

        assertArrayEquals(expected, array);
        assertTrue(metrics.getMaxRecursionDepth() <= 2 * Math.ceil(Math.log(array.length) / Math.log(2)) + 10);
    }

    @Test
    void testDuplicates() {
        int[] array = {4, 2, 4, 1, 2, 4, 1, 2};
        int[] expected = array.clone();
        Arrays.sort(expected);

        mergeSort.sort(array);
        assertArrayEquals(expected, array);
    }
}