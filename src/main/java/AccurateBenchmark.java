package com.example;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import java.util.concurrent.TimeUnit;
import java.util.Random;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
@State(Scope.Benchmark)
@Fork(value = 2, jvmArgs = {"-Xms2G", "-Xmx2G"})
@Warmup(iterations = 5, time = 1, timeUnit = TimeUnit.SECONDS)
@Measurement(iterations = 10, time = 1, timeUnit = TimeUnit.SECONDS)
@Threads(1)
public class AccurateBenchmark {
    
    private int[] array;
    private final int ARRAY_SIZE = 10000;

    @Setup
    public void setup() {
        Random random = new Random(42);
        
        array = new int[ARRAY_SIZE];
        for (int i = 0; i < ARRAY_SIZE; i++) {
            array[i] = random.nextInt(10000);
        }
    }
    
    @Benchmark
    public int sumNumbers() {
        int sum = 0;
        for (int i = 0; i <= 1000000; i++) {
            sum += i;
        }
        return sum;
    }

    @Benchmark
    public long fibonacci() {
        return fibonacciRecursive(30);
    }

    @Benchmark
    public int[] quickSort() {
        int[] arrayCopy = array.clone();
        quickSortAlgorithm(arrayCopy, 0, arrayCopy.length - 1);
        return arrayCopy;
    }

    private long fibonacciRecursive(int n) {
        if (n <= 1) return n;
        return fibonacciRecursive(n - 1) + fibonacciRecursive(n - 2);
    }

    private void quickSortAlgorithm(int[] arr, int low, int high) {
        if (low < high) {
            int pi = partition(arr, low, high);
            quickSortAlgorithm(arr, low, pi - 1);
            quickSortAlgorithm(arr, pi + 1, high);
        }
    }

    private int partition(int[] arr, int low, int high) {
        int pivot = arr[high];
        int i = (low - 1);
        for (int j = low; j < high; j++) {
            if (arr[j] < pivot) {
                i++;
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
            }
        }
        int temp = arr[i + 1];
        arr[i + 1] = arr[high];
        arr[high] = temp;
        return i + 1;
    }
    
    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(AccurateBenchmark.class.getSimpleName())
                .build();
        
        new Runner(opt).run();
    }
}