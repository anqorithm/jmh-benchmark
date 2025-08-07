package com.example;

public class InaccurateBenchmark {
    
    public static int sumNumbers(int n) {
        int sum = 0;
        for (int i = 0; i <= n; i++) {
            sum += i;
        }
        return sum;
    }
    
    public static void main(String[] args) {
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
    }
}