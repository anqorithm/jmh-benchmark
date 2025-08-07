package com.example;

import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;
import java.util.concurrent.TimeUnit;

@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MICROSECONDS)
@State(Scope.Thread)
@Fork(0)
@Warmup(iterations = 3, time = 1)
@Measurement(iterations = 5, time = 1)
public class AccurateBenchmark {
    
    @Benchmark
    public int sumNumbers() {
        int sum = 0;
        for (int i = 0; i <= 1000000; i++) {
            sum += i;
        }
        return sum;
    }
    
    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(AccurateBenchmark.class.getSimpleName())
                .build();
        
        new Runner(opt).run();
    }
}