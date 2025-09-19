package ru.yandex.practicum;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.algorithm.DeterministicSelect;
import ru.yandex.practicum.util.Metrics;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Random;

class DeterministicSelectTest {
    private DeterministicSelect select;
    private Metrics metrics;

    @BeforeEach
    void setUp() {
        metrics = new Metrics();
        select = new DeterministicSelect(metrics);
    }

    @Test
    void testEmptyArray() {
        assertThrows(IllegalArgumentException.class, () -> select.select(new int[]{}, 0));
    }

    @Test
    void testSingleElement() {
        int[] array = {42};
        assertEquals(42, select.select(array, 0));
    }

    @Test
    void testFirstElement() {
        int[] array = {3, 1, 4, 1, 5};
        Arrays.sort(array);
        int expected = array[0];
        assertEquals(expected, select.select(array.clone(), 0));
    }

    @Test
    void testLastElement() {
        int[] array = {3, 1, 4, 1, 5};
        Arrays.sort(array);
        int expected = array[array.length - 1];
        assertEquals(expected, select.select(array.clone(), array.length - 1));
    }

    @Test
    void testMedian() {
        int[] array = {7, 3, 1, 9, 5};
        Arrays.sort(array);
        int expected = array[array.length / 2];
        assertEquals(expected, select.select(array.clone(), array.length / 2));
    }

    @Test
    void testRandomTrials() {
        Random random = new Random(42);

        for (int trial = 0; trial < 100; trial++) {
            int size = random.nextInt(1000) + 1;
            int[] array = random.ints(size).toArray();
            int k = random.nextInt(size);

            int[] sorted = array.clone();
            Arrays.sort(sorted);
            int expected = sorted[k];

            metrics.reset();
            int actual = select.select(array.clone(), k);

            assertEquals(expected, actual,
                    "Trial " + trial + ": k=" + k + ", size=" + size);
        }
    }

    @Test
    void testDuplicates() {
        int[] array = {4, 2, 4, 1, 2, 4, 1, 2};
        Arrays.sort(array);

        for (int k = 0; k < array.length; k++) {
            int expected = array[k];
            assertEquals(expected, select.select(array.clone(), k));
        }
    }

    @Test
    void testSortedArray() {
        int[] array = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

        for (int k = 0; k < array.length; k++) {
            int expected = array[k];
            assertEquals(expected, select.select(array.clone(), k));
        }
    }

    @Test
    void testReverseSortedArray() {
        int[] array = {10, 9, 8, 7, 6, 5, 4, 3, 2, 1};
        int[] sorted = array.clone();
        Arrays.sort(sorted);

        for (int k = 0; k < array.length; k++) {
            int expected = sorted[k];
            assertEquals(expected, select.select(array.clone(), k));
        }
    }
}