package ru.yandex.practicum.util;

import lombok.Data;

import java.util.concurrent.TimeUnit;

@Data
public class Metrics {
    private int comparisons;
    private int maxRecursionDepth;
    private int currentRecursionDepth;
    private long startTime;
    private long endTime;

    public void startTimer() {
        startTime = System.nanoTime();
    }

    public void stopTimer() {
        endTime = System.nanoTime();
    }

    public long getElapsedTime(TimeUnit unit) {
        return unit.convert(endTime - startTime, TimeUnit.NANOSECONDS);
    }

    public void incrementComparisons(int count) {
        comparisons += count;
    }

    public void enterRecursion() {
        currentRecursionDepth++;
        maxRecursionDepth = Math.max(maxRecursionDepth, currentRecursionDepth);
    }

    public void exitRecursion() {
        currentRecursionDepth--;
    }

    public void reset() {
        comparisons = 0;
        maxRecursionDepth = 0;
        currentRecursionDepth = 0;
        startTime = 0;
        endTime = 0;
    }
}