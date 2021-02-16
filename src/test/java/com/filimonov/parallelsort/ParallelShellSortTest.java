package com.filimonov.parallelsort;

import java.util.Random;
import org.junit.jupiter.api.Test;

import static com.filimonov.parallelsort.ParallelShellSort.sort;
import static org.junit.jupiter.api.Assertions.fail;


/**
 * @author Tatarin Esli Che
 */
class ParallelShellSortTest {

    private final static int ARRAY_SIZE = 1_000_000;
    private final static int[] arr = new int[ARRAY_SIZE];

    @Test
    public void testSort() throws InterruptedException {
        Random r = new Random();
        for (int i = 0; i < ARRAY_SIZE; i++) {
            arr[i] = r.nextInt(ARRAY_SIZE) - ARRAY_SIZE / 2;
        }

        int[] arr1 = arr.clone();
        sort(arr1, 1);
        isSorted(arr1);

        int[] arr2 = arr.clone();
        sort(arr2,2);
        isSorted(arr2);

        int[] arr3 = arr.clone();
        sort(arr3, 4);
        isSorted(arr3);

        int[] arr8 = arr.clone();
        sort(arr8, 8);
        isSorted(arr8);

        int[] arr16 = arr.clone();
        sort(arr16, 16);
        isSorted(arr16);
    }

    private static void isSorted(int[] data){
        for(int i = 1; i < data.length; i++) {
            if (data[i - 1] > data[i]) {
                fail();
            }
        }
    }

}