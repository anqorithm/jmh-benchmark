package com.example;

import java.util.Random;

public class InaccurateBenchmark {
    
    public static int sumNumbers(int n) {
        int sum = 0;
        for (int i = 0; i <= n; i++) {
            sum += i;
        }
        return sum;
    }

    public static long fibonacci(int n) {
        if (n <= 1) return n;
        return fibonacci(n - 1) + fibonacci(n - 2);
    }

    public static void quickSort(int[] arr, int low, int high) {
        if (low < high) {
            int pi = partition(arr, low, high);
            quickSort(arr, low, pi - 1);
            quickSort(arr, pi + 1, high);
        }
    }

    private static int partition(int[] arr, int low, int high) {
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

    
    public static void main(String[] args) {
        System.out.println("=== Simple Sum Benchmark ===");
        long start = System.currentTimeMillis();
        
        int result = sumNumbers(1000000);
        
        long end = System.currentTimeMillis();
        
        System.out.println("Result: " + result);
        System.out.println("Time taken: " + (end - start) + " ms");
        
        System.out.println("\nRunning again (notice different times!):");
        for (int i = 0; i < 5; i++) {
            start = System.currentTimeMillis();
            result = sumNumbers(1000000);
            end = System.currentTimeMillis();
            System.out.println("Run " + (i+1) + ": " + (end - start) + " ms");
        }

        System.out.println("\n=== Complex Algorithm Benchmarks ===");
        
        System.out.println("\nFibonacci(30) Benchmark:");
        for (int run = 1; run <= 5; run++) {
            start = System.currentTimeMillis();
            long fibResult = fibonacci(30);
            end = System.currentTimeMillis();
            System.out.println("Run " + run + ": " + (end - start) + " ms (Result: " + fibResult + ")");
        }

        System.out.println("\nQuickSort(10000 elements) Benchmark:");
        Random random = new Random(42);
        for (int run = 1; run <= 5; run++) {
            int[] array = new int[10000];
            for (int i = 0; i < 10000; i++) {
                array[i] = random.nextInt(10000);
            }
            
            start = System.currentTimeMillis();
            quickSort(array, 0, array.length - 1);
            end = System.currentTimeMillis();
            System.out.println("Run " + run + ": " + (end - start) + " ms");
        }

    }
}