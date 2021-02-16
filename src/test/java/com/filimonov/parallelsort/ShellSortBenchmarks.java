package com.filimonov.parallelsort;

import java.util.Random;
import java.util.concurrent.TimeUnit;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Warmup;

import static com.filimonov.parallelsort.ParallelShellSort.sort;

/**
 * @author Artem Filimonov
 */

@Fork(value = 1)
@Warmup(iterations = 30, time = 1)
@Measurement(iterations = 30, time = 1)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@BenchmarkMode(Mode.AverageTime)
public class ShellSortBenchmarks {

    private final static int ARRAY_SIZE = 1_000_000;
    private final static int[] arr = new int[ARRAY_SIZE];

    public static void main(String[] args) throws Exception {
        Random r = new Random();
        for (int i = 0; i < ARRAY_SIZE; i++) {
            arr[i] = r.nextInt(ARRAY_SIZE) - ARRAY_SIZE / 2;
        }
        org.openjdk.jmh.Main.main(args);
    }

    @Benchmark
    public void oneThreads() throws InterruptedException {
        sort(arr.clone(), 1);
    }

    @Benchmark
    public void twoThreads() throws InterruptedException {
        sort(arr.clone(), 2);
    }

    @Benchmark
    public void threeThreads() throws InterruptedException {
        sort(arr.clone(), 3);
    }

    @Benchmark
    public void fourThreads() throws InterruptedException {
        sort(arr.clone(), 4);
    }

    @Benchmark
    public void eightThreads() throws InterruptedException {
        sort(arr.clone(), 8);
    }

    @Benchmark
    public void sixteenThreads() throws InterruptedException {
        sort(arr.clone(), 16);
    }
}
