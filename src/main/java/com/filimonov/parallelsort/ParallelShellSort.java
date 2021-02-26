package com.filimonov.parallelsort;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static com.filimonov.parallelsort.ShellSort.*;

/**
 * @author Artem Filimonov
 */

public class ParallelShellSort {

    private ConcurrentHashMap<String, Long> threads;
    private volatile int iterCounter;

    public void sort(int[] array, int threadsCount) throws InterruptedException {
        if (threadsCount < 2) {
            ShellSort.sort(array);
            return;
        }

        iterCounter = 0;
        threads = new ConcurrentHashMap<>();
        int h = calcH(array);

        sort(array, threadsCount, h);
        if (threads.size() != threadsCount) throw new InterruptedException();
    }

    private void sort(int[] array, int threadsCount, int h) throws InterruptedException {

        ExecutorService executor = Executors.newFixedThreadPool(threadsCount);
        List<Callable<Object>> todo = new ArrayList<>(threadsCount);

        while (h >= 2) {
            todo.clear();
            for (int i = 0; i < threadsCount && i < h; i++) {
                todo.add(new Task(array, h, i, threadsCount));
            }
            executor.invokeAll(todo);
            h /= 3;
        }

        hSort(array, 1);
        executor.shutdown();
    }

    private void insertSort(int[] array, int h, int i, int threadCount) {
        int length = array.length;
        for (int k = i; k < h; k += threadCount)
            for (i = k; i < length; i += h) {
                sortIter(array, h, i);
            }
        inc();
        threads.put(Thread.currentThread().getName(), Thread.currentThread().getId());
    }

    private synchronized void inc() {
        iterCounter++;
    }

    class Task implements Callable<Object> {

        private final int[] array;
        private final int h, i, threadsCount;

        private Task(int[] array, int h, int i, int threadsCount) {
            this.array = array;
            this.h = h;
            this.i = i;
            this.threadsCount = threadsCount;
        }

        @Override
        public Object call() {
            insertSort(array, h, i, threadsCount);
            return Thread.currentThread().getName();
        }
    }

}
