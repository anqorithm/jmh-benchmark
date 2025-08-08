# JMH Benchmark

Simple example showing why `System.currentTimeMillis()` is inaccurate for benchmarking.

## Table of Contents

- [Introduction](#introduction)
- [Files](#files)
- [How to Run](#how-to-run)
- [The Difference](#the-difference)
- [Benchmark Results](#benchmark-results)
  - [InaccurateBenchmark (System.currentTimeMillis())](#inaccuratebenchmark-systemcurrenttimemillis)
  - [AccurateBenchmark (JMH)](#accuratebenchmark-jmh)
  - [Performance Comparison Charts](#performance-comparison-charts)
  - [Key Observations](#key-observations)
- [Examples](#examples)
  - [Example 1: Fibonacci Calculation (Recursive)](#example-1-fibonacci-calculation-recursive)
  - [Example 2: Sorting Arrays](#example-2-sorting-arrays)
  - [Example 3: Simple Sum](#example-3-simple-sum)
  - [Why These Examples Show the Problem](#why-these-examples-show-the-problem)
- [Conclusion](#conclusion)
- [Author](#author)
- [License](#license)

## Introduction

Many developers use `System.currentTimeMillis()` to measure how fast their code runs, but this method has serious problems. This project shows why the old way of timing code doesn't work well and how JMH (Java Microbenchmark Harness) gives you accurate results.

You'll see real examples that compare the basic timing method with JMH's better approach. You'll learn why your current measurements might be wrong and how to get results you can trust.

## Files

- `InaccurateBenchmark.java` - Uses System.currentTimeMillis() (unreliable)
- `AccurateBenchmark.java` - Uses JMH (reliable)

## How to Run

```bash
# Compile
mvn clean compile

# Run inaccurate benchmark
mvn exec:java -Dexec.mainClass="com.example.InaccurateBenchmark"

# Run accurate benchmark with JMH
mvn exec:java -Dexec.mainClass="com.example.AccurateBenchmark"
```

## The Difference

**Inaccurate:** Shows 0ms, 1ms, 2ms randomly  
**Accurate:** Shows 264.519 ± 16.871 microseconds

JMH handles warmup, multiple iterations, and statistics properly.

## Benchmark Results

### InaccurateBenchmark (System.currentTimeMillis())
```
Result: 1784293664
Time taken: 2 ms

Running again (notice different times!):
Run 1: 2 ms
Run 2: 0 ms
Run 3: 0 ms
Run 4: 0 ms
Run 5: 0 ms
```

### AccurateBenchmark (JMH)
```
# JMH version: 1.37
# VM version: JDK 21.0.8, OpenJDK 64-Bit Server VM, 21.0.8+9-Ubuntu-0ubuntu124.04.1
# Warmup: 10 iterations, 2 s each
# Measurement: 20 iterations, 3 s each
# Threads: 1 thread
# Benchmark mode: Average time, time/op

Benchmark                     Mode  Cnt        Score       Error  Units
AccurateBenchmark.fibonacci   avgt   20  3022751.459 ± 48154.034  ns/op
AccurateBenchmark.quickSort   avgt   20   451102.375 ±  8366.031  ns/op
AccurateBenchmark.sumNumbers  avgt   20   255269.231 ±  2623.039  ns/op
```

### Performance Comparison Charts

<div align="center">

![Figure 1: Fibonacci Benchmark Comparison](/assets/1.png)

*Figure 1: Fibonacci(30) performance comparison - JMH shows consistent ~3ms execution time vs System.currentTimeMillis() inconsistent 2-3ms readings*

</div>

<div align="center">

![Figure 2: QuickSort Benchmark Comparison](/assets/2.png)

*Figure 2: QuickSort(10,000 elements) performance comparison - JMH measures precise 451μs vs System.currentTimeMillis() unreliable 0-1ms readings*

</div>

<div align="center">

![Figure 3: Sum Numbers Benchmark Comparison](/assets/3.png)

*Figure 3: Sum calculation performance comparison - JMH shows consistent 255μs vs System.currentTimeMillis() varying 0-4ms measurements*

</div>

<div align="center">

![Figure 4: JMH Statistical Analysis](/assets/4.png)

*Figure 4: JMH provides statistical confidence intervals and error margins for reliable performance analysis*

</div>

<div align="center">

![Figure 5: Timer Resolution Impact](/assets/5.png)

*Figure 5: System.currentTimeMillis() limited to ~1ms resolution makes sub-millisecond measurements impossible*

</div>

<div align="center">

![Figure 6: JVM Warmup Effects](/assets/6.png)

*Figure 6: JMH handles JIT compilation warmup properly while System.currentTimeMillis() includes cold-start overhead*

</div>

### Key Observations
- **InaccurateBenchmark**: Shows inconsistent results (0-2 ms) due to timer resolution limitations
- **AccurateBenchmark**: Provides precise measurement (256.476 ± 9.861 microseconds) with statistical confidence
- JMH properly handles JVM warmup, JIT compilation effects, and provides statistical analysis

## Examples

### Example 1: Fibonacci Calculation (Recursive)

**Inaccurate Method Results:**
```
Fibonacci(30) - Run 1: 3 ms (Result: 832040)
Fibonacci(30) - Run 2: 3 ms (Result: 832040)
Fibonacci(30) - Run 3: 2 ms (Result: 832040)
Fibonacci(30) - Run 4: 3 ms (Result: 832040)
Fibonacci(30) - Run 5: 3 ms (Result: 832040)
```

**JMH Results:**
```
Benchmark                     Mode  Cnt        Score       Error  Units
AccurateBenchmark.fibonacci   avgt   20  3022751.459 ± 48154.034  ns/op
```

### Example 2: Sorting Arrays

**Inaccurate Method Results:**
```
QuickSort(10,000 elements) - Run 1: 1 ms
QuickSort(10,000 elements) - Run 2: 0 ms
QuickSort(10,000 elements) - Run 3: 1 ms
QuickSort(10,000 elements) - Run 4: 1 ms
QuickSort(10,000 elements) - Run 5: 0 ms
```

**JMH Results:**
```
Benchmark                     Mode  Cnt       Score      Error  Units
AccurateBenchmark.quickSort   avgt   20  451102.375 ± 8366.031  ns/op
```

### Example 3: Simple Sum

**Inaccurate Method Results:**
```
Sum Numbers - Initial: 4 ms
Sum Numbers - Run 1: 1 ms
Sum Numbers - Run 2: 0 ms
Sum Numbers - Run 3: 0 ms
Sum Numbers - Run 4: 0 ms
Sum Numbers - Run 5: 0 ms
```

**JMH Results:**
```
Benchmark                     Mode  Cnt       Score      Error  Units
AccurateBenchmark.sumNumbers  avgt   20  255269.231 ± 2623.039  ns/op
```

### Why These Examples Show the Problem

1. **Timer Resolution**: System.currentTimeMillis() has ~1ms resolution, making it useless for operations taking microseconds
2. **JVM Warmup**: The first few runs are slower due to JIT compilation, which System.currentTimeMillis() doesn't account for
3. **Statistical Analysis**: JMH provides confidence intervals and error margins, showing measurement reliability
4. **Consistent Environment**: JMH controls for external factors that affect timing measurements

## Conclusion

This project shows why you should never use `System.currentTimeMillis()` to test how fast your Java code runs. The difference is huge: the old method can show 0ms for operations that actually take hundreds of microseconds, while JMH gives you exact measurements you can trust.

When you use JMH, you get accurate results that help you make your code faster. It handles all the complex stuff like JVM warmup and gives you real numbers instead of guesses. This means you can actually improve your code's speed instead of wasting time on bad measurements.

## Author

**Abdullah Alqahtani**
- Email: [anqorithm@protonmail.com](mailto:anqorithm@protonmail.com)

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.