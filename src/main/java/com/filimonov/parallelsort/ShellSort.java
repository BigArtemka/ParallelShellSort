package com.filimonov.parallelsort;

/**
 * @author Artem Filimonov
 */

public class ShellSort {

    public static void sort(int[] array) {
        int h = calcH(array);

        while (h >= 1) {
            hSort(array, h);
            h = h / 3;
        }
    }

    static void hSort(int[] array, int h) {
        int length = array.length;
        for (int i = h; i < length; i++) {
            sortIter(array, h, i);
        }
    }

    static void sortIter(int[] array, int h, int i) {
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

    static int calcH(int[] array) {
        int h = 1;
        while (h * 3 < array.length)
            h = h * 3 + 1;
        return h;
    }

}
