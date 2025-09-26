package ru.yandex.practicum.util;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CSVMetricsWriter {
    private final String filename;
    private final List<String[]> data;

    public CSVMetricsWriter(String filename) {
        this.filename = filename;
        this.data = new ArrayList<>();
        data.add(new String[]{
                "Algorithm", "Size", "Time(ns)", "Comparisons", "MaxRecursionDepth", "Description"
        });
    }

    public void addRecord(String algorithm, int size, long timeNs,
                          int comparisons, int maxDepth, String description) {
        data.add(new String[]{
                algorithm,
                String.valueOf(size),
                String.valueOf(timeNs),
                String.valueOf(comparisons),
                String.valueOf(maxDepth),
                description
        });
    }

    public void writeToFile() throws IOException {
        try (FileWriter writer = new FileWriter(filename)) {
            for (String[] record : data) {
                writer.write(String.join(",", record));
                writer.write("\n");
            }
        }
    }
}