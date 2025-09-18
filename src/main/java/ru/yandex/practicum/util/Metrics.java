package ru.yandex.practicum.util;

import lombok.Data;


@Data
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

    public void recordRecursionDepth() {
        currentRecursionDepth++;
        maxRecursionDepth = Math.max(maxRecursionDepth, currentRecursionDepth);
    }

    public void exitRecursion() {
        currentRecursionDepth--;
    }


    public void reset() {
        comparisons = 0;
        allocations = 0;
        maxRecursionDepth = 0;
        currentRecursionDepth = 0;
    }
}