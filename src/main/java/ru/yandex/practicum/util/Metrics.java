package ru.yandex.practicum.util;

public class Metrics {
    private int comparisons;
    private int allocations;
    private int maxRecursionDepth;
    private int currentRecursionDepth;

    public void incrementComparisons(int count) {
        comparisons += count;
    }

    public void incrementAllocations(int count) {
        allocations += count;
    }

    public void enterRecursion() {
        currentRecursionDepth++;
        maxRecursionDepth = Math.max(maxRecursionDepth, currentRecursionDepth);
    }

    public void exitRecursion() {
        currentRecursionDepth--;
    }

    // Getters and reset methods
    public int getComparisons() { return comparisons; }
    public int getAllocations() { return allocations; }
    public int getMaxRecursionDepth() { return maxRecursionDepth; }
    public int getCurrentRecursionDepth() { return currentRecursionDepth; }

    public void reset() {
        comparisons = 0;
        allocations = 0;
        maxRecursionDepth = 0;
        currentRecursionDepth = 0;
    }
}