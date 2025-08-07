# JMH Benchmark

Simple example showing why `System.currentTimeMillis()` is inaccurate for benchmarking.

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
# Warmup: 3 iterations, 1 s each
# Measurement: 5 iterations, 1 s each
# Threads: 1 thread
# Benchmark mode: Average time, time/op

Result "com.example.AccurateBenchmark.sumNumbers":
  256.476 ±(99.9%) 9.861 us/op [Average]
  (min, avg, max) = (253.273, 256.476, 259.972), stdev = 2.561
  CI (99.9%): [246.615, 266.337] (assumes normal distribution)

Benchmark                     Mode  Cnt    Score   Error  Units
AccurateBenchmark.sumNumbers  avgt    5  256.476 ± 9.861  us/op
```

### Benchmark Visualization

![benchmark](/assets/jmh.png)


### Key Observations
- **InaccurateBenchmark**: Shows inconsistent results (0-2 ms) due to timer resolution limitations
- **AccurateBenchmark**: Provides precise measurement (256.476 ± 9.861 microseconds) with statistical confidence
- JMH properly handles JVM warmup, JIT compilation effects, and provides statistical analysis



## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.