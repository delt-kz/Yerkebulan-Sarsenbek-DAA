package ru.yandex.practicum.algorithm;

import ru.yandex.practicum.util.Metrics;
import ru.yandex.practicum.util.Point2D;

import java.util.Arrays;
import java.util.Comparator;

public class ClosestPair {
    private final Metrics metrics;

    public ClosestPair(Metrics metrics) {
        this.metrics = metrics;
    }

    public double findClosestPair(Point2D[] points) {
        if (points == null || points.length < 2) {
            throw new IllegalArgumentException("At least 2 points required");
        }

        Point2D[] pointsByX = points.clone();
        Arrays.sort(pointsByX, Comparator.comparingDouble(Point2D::getX).thenComparingDouble(Point2D::getY));

        Point2D[] pointsByY = points.clone();
        Arrays.sort(pointsByY, Comparator.comparingDouble(Point2D::getY).thenComparingDouble(Point2D::getX));

        return closestPair(pointsByX, pointsByY, 0, points.length - 1);
    }

    private double closestPair(Point2D[] pointsByX, Point2D[] pointsByY, int left, int right) {
        metrics.enterRecursion();
        try {
            int n = right - left + 1;

            if (n <= 3) {
                return bruteForce(pointsByX, left, right);
            }

            int mid = left + (right - left) / 2;
            Point2D midPoint = pointsByX[mid];

            Point2D[] leftY = new Point2D[mid - left + 1];
            Point2D[] rightY = new Point2D[right - mid];
            int leftIdx = 0, rightIdx = 0;

            for (Point2D point : pointsByY) {
                if (point.getX() < midPoint.getX() ||
                        (point.getX() == midPoint.getX() && point.getY() <= midPoint.getY())) {
                    if (leftIdx < leftY.length) {
                        leftY[leftIdx++] = point;
                    }
                } else {
                    if (rightIdx < rightY.length) {
                        rightY[rightIdx++] = point;
                    }
                }
            }

            double leftMin = closestPair(pointsByX, leftY, left, mid);
            double rightMin = closestPair(pointsByX, rightY, mid + 1, right);
            double minDistance = Math.min(leftMin, rightMin);

            Point2D[] strip = new Point2D[n];
            int stripSize = 0;
            for (Point2D point : pointsByY) {
                if (Math.abs(point.getX() - midPoint.getX()) < minDistance) {
                    strip[stripSize++] = point;
                }
            }

            double stripMin = stripClosest(strip, stripSize, minDistance);
            return Math.min(minDistance, stripMin);
        } finally {
            metrics.exitRecursion();
        }
    }

    private double bruteForce(Point2D[] points, int left, int right) {
        double minDistance = Double.MAX_VALUE;

        for (int i = left; i <= right; i++) {
            for (int j = i + 1; j <= right; j++) {
                double distance = points[i].distanceTo(points[j]);
                metrics.incrementComparisons(1);
                minDistance = Math.min(minDistance, distance);
            }
        }

        return minDistance;
    }

    private double stripClosest(Point2D[] strip, int size, double minDistance) {
        double min = minDistance;

        for (int i = 0; i < size; i++) {
            for (int j = i + 1; j < size && (strip[j].getY() - strip[i].getY()) < min; j++) {
                double distance = strip[i].distanceTo(strip[j]);
                metrics.incrementComparisons(1);
                min = Math.min(min, distance);
            }
        }

        return min;
    }
}