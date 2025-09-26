package ru.yandex.practicum.client;

import ru.yandex.practicum.algorithm.ClosestPair;
import ru.yandex.practicum.algorithm.DeterministicSelect;
import ru.yandex.practicum.algorithm.MergeSort;
import ru.yandex.practicum.algorithm.QuickSort;
import ru.yandex.practicum.util.CSVMetricsWriter;
import ru.yandex.practicum.util.Metrics;
import ru.yandex.practicum.util.Point2D;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class AlgorithmBenchmark {
    private final Random random = new Random(42);
    private final CSVMetricsWriter csvWriter;

    public AlgorithmBenchmark(String csvFilename) {
        this.csvWriter = new CSVMetricsWriter(csvFilename);
    }

    public void runBenchmarks() throws Exception {
        int[] sizes = {100, 500, 1000, 5000, 10000, 50000, 100000};

        for (int size : sizes) {
            if (size <= 100000) {
                benchmarkMergeSort(size);
                benchmarkQuickSort(size);
            }
            if (size <= 50000) {
                benchmarkDeterministicSelect(size);
            }
            if (size <= 20000) {
                benchmarkClosestPair(size);
            }
        }

        csvWriter.writeToFile();
    }

    private void benchmarkMergeSort(int size) {
        int[] array = generateRandomArray(size);
        Metrics metrics = new Metrics();
        MergeSort sorter = new MergeSort(metrics);

        metrics.startTimer();
        sorter.sort(array);
        metrics.stopTimer();

        csvWriter.addRecord(
                "MergeSort", size,
                metrics.getElapsedTime(TimeUnit.NANOSECONDS),
                metrics.getComparisons(),
                metrics.getAllocations(),
                metrics.getMaxRecursionDepth(),
                "Random array"
        );
    }

    private void benchmarkQuickSort(int size) {
        int[] array = generateRandomArray(size);
        Metrics metrics = new Metrics();
        QuickSort sorter = new QuickSort(metrics);

        metrics.startTimer();
        sorter.sort(array);
        metrics.stopTimer();

        csvWriter.addRecord(
                "QuickSort", size,
                metrics.getElapsedTime(TimeUnit.NANOSECONDS),
                metrics.getComparisons(),
                metrics.getAllocations(),
                metrics.getMaxRecursionDepth(),
                "Random array"
        );
    }

    private void benchmarkDeterministicSelect(int size) {
        int[] array = generateRandomArray(size);
        Metrics metrics = new Metrics();
        DeterministicSelect selector = new DeterministicSelect(metrics);

        int k = size / 2;

        metrics.startTimer();
        int result = selector.select(array, k);
        metrics.stopTimer();

        csvWriter.addRecord(
                "DeterministicSelect", size,
                metrics.getElapsedTime(TimeUnit.NANOSECONDS),
                metrics.getComparisons(),
                metrics.getAllocations(),
                metrics.getMaxRecursionDepth(),
                "Median selection"
        );
    }

    private void benchmarkClosestPair(int size) {
        Point2D[] points = generateRandomPoints(size);
        Metrics metrics = new Metrics();
        ClosestPair finder = new ClosestPair(metrics);

        metrics.startTimer();
        double distance = finder.findClosestPair(points);
        metrics.stopTimer();

        csvWriter.addRecord(
                "ClosestPair", size,
                metrics.getElapsedTime(TimeUnit.NANOSECONDS),
                metrics.getComparisons(),
                metrics.getAllocations(),
                metrics.getMaxRecursionDepth(),
                "Random points"
        );
    }

    private int[] generateRandomArray(int size) {
        int[] array = new int[size];
        for (int i = 0; i < size; i++) {
            array[i] = random.nextInt(size * 10);
        }
        return array;
    }

    private Point2D[] generateRandomPoints(int size) {
        Point2D[] points = new Point2D[size];
        for (int i = 0; i < size; i++) {
            points[i] = new Point2D(random.nextDouble() * 1000, random.nextDouble() * 1000);
        }
        return points;
    }

    public static void main(String[] args) {
        try {
            AlgorithmBenchmark benchmark = new AlgorithmBenchmark("algorithm_metrics.csv");
            benchmark.runBenchmarks();
            System.out.println("Benchmark completed. Results saved to algorithm_metrics.csv");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}