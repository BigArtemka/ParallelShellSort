package com.filimonov.parallelsort;

import java.net.Inet4Address;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author Artem Filimonov
 */
public class ParallelShellSort {

    public static void sort(int[] array, int threadsCount) throws InterruptedException {
        int h = 1;
        while (h * 3 < array.length)
            h = h * 3 + 1;
        sort(array, threadsCount, h);
    }

    private static void sort(int[] array, int threadsCount, int h) throws InterruptedException {
        List<Thread> threads = new ArrayList<>();

        if (h < 2) {
            insertSort(array);
            return;
        }

        for (int i = 0; i < threadsCount && i < h; i++) {
            int finalI = i;
            threads.add(new Thread(() -> insertSort(array, h, finalI, threadsCount)));
            threads.get(i).start();
        }

        for (Thread thread : threads) {
            thread.join();
        }

        sort(array, threadsCount, h / 3);
    }

    private static void insertSort(int[] array, int h, int i, int threadCount) {
        int temp;
        int length = array.length;
        for (int k = i; k < h; k += threadCount)
            for (i = k; i < length; i += h) {
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

}
