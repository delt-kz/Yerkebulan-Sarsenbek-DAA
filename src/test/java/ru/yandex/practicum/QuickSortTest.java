package ru.yandex.practicum;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.algorithm.QuickSort;
import ru.yandex.practicum.util.Metrics;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Random;

class QuickSortTest {
    private QuickSort quickSort;
    private Metrics metrics;

    @BeforeEach
    void setUp() {
        metrics = new Metrics();
        quickSort = new QuickSort(metrics);
    }

    @Test
    void testEmptyArray() {
        int[] array = {};
        quickSort.sort(array);
        assertArrayEquals(new int[]{}, array);
    }

    @Test
    void testSingleElement() {
        int[] array = {5};
        quickSort.sort(array);
        assertArrayEquals(new int[]{5}, array);
    }

    @Test
    void testSortedArray() {
        int[] array = {1, 2, 3, 4, 5};
        quickSort.sort(array);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, array);
    }

    @Test
    void testReverseSortedArray() {
        int[] array = {5, 4, 3, 2, 1};
        quickSort.sort(array);
        assertArrayEquals(new int[]{1, 2, 3, 4, 5}, array);
    }

    @Test
    void testRandomArray() {
        int[] array = {3, 1, 4, 1, 5, 9, 2, 6};
        int[] expected = array.clone();
        Arrays.sort(expected);

        quickSort.sort(array);
        assertArrayEquals(expected, array);
    }

    @Test
    void testAdversarialArray() {
        int[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
        int[] expected = array.clone();
        Arrays.sort(expected);

        metrics.reset();
        quickSort.sort(array);

        assertArrayEquals(expected, array);
        assertTrue(metrics.getMaxRecursionDepth() <= 2 * Math.ceil(Math.log(array.length) / Math.log(2)) + 10);
    }

    @Test
    void testAllEqualElements() {
        int[] array = {5, 5, 5, 5, 5};
        quickSort.sort(array);
        assertArrayEquals(new int[]{5, 5, 5, 5, 5}, array);
    }

    @Test
    void testRecursionDepthBounded() {
        Random random = new Random(42);
        int[] array = random.ints(10000).toArray();

        metrics.reset();
        quickSort.sort(array);

        double expectedMaxDepth = 2 * Math.ceil(Math.log(array.length) / Math.log(2)) + 20;
        assertTrue(metrics.getMaxRecursionDepth() <= expectedMaxDepth,
                "Recursion depth " + metrics.getMaxRecursionDepth() +
                        " exceeds expected bound " + expectedMaxDepth);
    }

    @Test
    void testLargeRandomArray() {
        Random random = new Random(42);
        int[] array = random.ints(10000).toArray();
        int[] expected = array.clone();
        Arrays.sort(expected);

        quickSort.sort(array);
        assertArrayEquals(expected, array);
    }
}