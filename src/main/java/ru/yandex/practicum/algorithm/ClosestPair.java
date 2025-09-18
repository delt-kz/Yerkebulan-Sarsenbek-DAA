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
        Arrays.sort(pointsByX, Comparator.comparingDouble(Point2D::getX));

        Point2D[] pointsByY = points.clone();
        Arrays.sort(pointsByY, Comparator.comparingDouble(Point2D::getY));

        return closestPair(pointsByX, pointsByY, 0, points.length - 1);
    }

    private double closestPair(Point2D[] pointsByX, Point2D[] pointsByY, int left, int right) {
        metrics.recordRecursionDepth();

        int n = right - left + 1;

        if (n <= 3) {
            return bruteForce(pointsByX, left, right);
        }

        int mid = left + (right - left) / 2;
        Point2D midPoint = pointsByX[mid];

        Point2D[] leftY = Arrays.copyOfRange(pointsByY, left, mid + 1);
        Point2D[] rightY = Arrays.copyOfRange(pointsByY, mid + 1, right + 1);

        double leftMin = closestPair(pointsByX, leftY, left, mid);
        double rightMin = closestPair(pointsByX, rightY, mid + 1, right);
        double minDistance = Math.min(leftMin, rightMin);

        Point2D[] strip = new Point2D[n];
        int stripSize = 0;

        for (int i = left; i <= right; i++) {
            if (Math.abs(pointsByY[i].getX() - midPoint.getX()) < minDistance) {
                strip[stripSize++] = pointsByY[i];
            }
        }

        double stripMin = stripClosest(strip, stripSize, minDistance);
        return Math.min(minDistance, stripMin);
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