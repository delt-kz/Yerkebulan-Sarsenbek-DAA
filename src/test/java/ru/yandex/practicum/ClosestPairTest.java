package ru.yandex.practicum;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.algorithm.ClosestPair;
import ru.yandex.practicum.util.Metrics;
import ru.yandex.practicum.util.Point2D;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.Random;

class ClosestPairTest {
    private ClosestPair closestPair;
    private Metrics metrics;

    @BeforeEach
    void setUp() {
        metrics = new Metrics();
        closestPair = new ClosestPair(metrics);
    }

    @Test
    void testTwoPoints() {
        Point2D[] points = {
                new Point2D(0, 0),
                new Point2D(1, 1)
        };
        double expected = Math.sqrt(2);
        double actual = closestPair.findClosestPair(points);
        assertEquals(expected, actual, 1e-10);
    }

    @Test
    void testThreePoints() {
        Point2D[] points = {
                new Point2D(0, 0),
                new Point2D(1, 1),
                new Point2D(2, 2)
        };
        double expected = Math.sqrt(2);
        double actual = closestPair.findClosestPair(points);
        assertEquals(expected, actual, 1e-10);
    }

    @Test
    void testHorizontalLine() {
        Point2D[] points = {
                new Point2D(0, 0),
                new Point2D(1, 0),
                new Point2D(2, 0),
                new Point2D(3, 0)
        };
        double expected = 1.0;
        double actual = closestPair.findClosestPair(points);
        assertEquals(expected, actual, 1e-10);
    }

    @Test
    void testVerticalLine() {
        Point2D[] points = {
                new Point2D(0, 0),
                new Point2D(0, 1),
                new Point2D(0, 2),
                new Point2D(0, 3)
        };
        double expected = 1.0;
        double actual = closestPair.findClosestPair(points);
        assertEquals(expected, actual, 1e-10);
    }

    @Test
    void testSmallRandomSet() {
        Point2D[] points = {
                new Point2D(1.2, 3.4),
                new Point2D(5.6, 7.8),
                new Point2D(9.0, 1.2),
                new Point2D(3.4, 5.6)
        };

        double expected = bruteForceClosest(points);
        double actual = closestPair.findClosestPair(points);

        assertEquals(expected, actual, 1e-10);
    }

    @Test
    void testDuplicatePoints() {
        Point2D[] points = {
                new Point2D(1, 1),
                new Point2D(1, 1),
                new Point2D(2, 2),
                new Point2D(3, 3)
        };
        double expected = 0.0;
        double actual = closestPair.findClosestPair(points);
        assertEquals(expected, actual, 1e-10);
    }

    @Test
    void testSmallRandomSetsAgainstBruteForce() {
        Random random = new Random(42);

        for (int size = 2; size <= 50; size++) {
            Point2D[] points = generateRandomPoints(size, random);

            double expected = bruteForceClosest(points);
            double actual = closestPair.findClosestPair(points);

            assertEquals(expected, actual, 1e-10,
                    "Failed for size " + size);
        }
    }

    @Test
    void testMediumSizePerformance() {
        Random random = new Random(42);
        Point2D[] points = generateRandomPoints(2000, random);

        double result = closestPair.findClosestPair(points);
        assertTrue(result >= 0, "Result should be non-negative");
    }

    private Point2D[] generateRandomPoints(int n, Random random) {
        Point2D[] points = new Point2D[n];
        for (int i = 0; i < n; i++) {
            points[i] = new Point2D(random.nextDouble() * 1000, random.nextDouble() * 1000);
        }
        return points;
    }

    private double bruteForceClosest(Point2D[] points) {
        double minDistance = Double.MAX_VALUE;

        for (int i = 0; i < points.length; i++) {
            for (int j = i + 1; j < points.length; j++) {
                double distance = points[i].distanceTo(points[j]);
                minDistance = Math.min(minDistance, distance);
            }
        }

        return minDistance;
    }
}