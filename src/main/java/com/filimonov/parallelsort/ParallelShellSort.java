package com.filimonov.parallelsort;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author Artem Filimonov
 */
public class ParallelShellSort {

    private static ConcurrentHashMap<String, Long> threads;
    private static volatile int iterCounter;

    public static void sort(int[] array, int threadsCount) throws InterruptedException {
        if (threadsCount < 2) {
            sort(array);
            return;
        }
        iterCounter = 0;
        threads = new ConcurrentHashMap<>();

        int h = 1;
        while (h * 3 < array.length)
            h = h * 3 + 1;

        sort(array, threadsCount, h);
        if (threads.size() != threadsCount) throw new InterruptedException();
    }

    private static void sort(int[] array, int threadsCount, int h) throws InterruptedException {

        ExecutorService executor = Executors.newFixedThreadPool(threadsCount);
        List<Callable<Object>> todo = new ArrayList<>(threadsCount);

        for (int i = 0; i < threadsCount && i < h; i++) {
            todo.add(new Task(array, h, i, threadsCount));
        }

        h /= 3;

        while (h > 2) {
            for (int i = 0; i < threadsCount && i < h; i++) {
                todo.set(i, new Task(array, h, i, threadsCount));
            }
            executor.invokeAll(todo);
            h /= 3;
        }

        insertSort(array);
        executor.shutdown();
    }

    private static void insertSort(int[] array, int h, int i, int threadCount) {
        int length = array.length;
        for (int k = i; k < h; k += threadCount)
            for (i = k; i < length; i += h) {
                sortIter(array, h, i);
            }
        inc();
        threads.put(Thread.currentThread().getName(), Thread.currentThread().getId());
    }

    private static synchronized void inc() {
        iterCounter++;
    }

    private static void insertSort(int[] array) {
        for (int left = 0; left < array.length; left++) {
            int value = array[left];
            int i = left - 1;
            for (; i >= 0; i--) {
                if (value < array[i]) {
                    array[i + 1] = array[i];
                } else {
                    break;
                }
            }
            array[i + 1] = value;
        }
    }

    static class Task implements Callable<Object> {

        private final int[] array;
        private final int finalH, finalI, threadsCount;

        private Task(int[] array, int finalH, int finalI, int threadsCount) {
            this.array = array;
            this.finalH = finalH;
            this.finalI = finalI;
            this.threadsCount = threadsCount;
        }

        @Override
        public Object call() {
            insertSort(array, finalH, finalI, threadsCount);
            return Thread.currentThread().getName();
        }
    }


    private static void sort(int[] array) {
        int h = 1;
        while (h * 3 < array.length)
            h = h * 3 + 1;

        while (h >= 1) {
            hSort(array, h);
            h = h / 3;
        }
    }

    private static void hSort(int[] array, int h) {
        int length = array.length;
        for (int i = h; i < length; i++) {
            sortIter(array, h, i);
        }
    }

    private static void sortIter(int[] array, int h, int i) {
        int temp;
        for (int j = i; j >= h; j = j - h) {
            if (array[j] < array[j - h]) {
                temp = array[j];
                array[j] = array[j - h];
                array[j - h] = temp;
            } else
                break;
        }
    }

}
