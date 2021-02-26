package com.filimonov.parallelsort;

import java.util.Random;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.fail;


/**
 * @author Tatarin Esli Che
 */
class ParallelShellSortTest {

    private final static int ARRAY_SIZE = 1_000_000;
    private final static int[] arr = new int[ARRAY_SIZE];

    @Test
    public void testSort() throws InterruptedException {
        ParallelShellSort parallelShellSort = new ParallelShellSort();
        Random r = new Random();
        for (int i = 0; i < ARRAY_SIZE; i++) {
            arr[i] = r.nextInt(ARRAY_SIZE) - ARRAY_SIZE / 2;
        }

        int[] arr1 = arr.clone();
        parallelShellSort.sort(arr1, 1);
        isSorted(arr1);

        int[] arr2 = arr.clone();
        parallelShellSort.sort(arr2,2);
        isSorted(arr2);

        int[] arr3 = arr.clone();
        parallelShellSort.sort(arr3, 4);
        isSorted(arr3);

        int[] arr8 = arr.clone();
        parallelShellSort.sort(arr8, 8);
        isSorted(arr8);

        int[] arr16 = arr.clone();
        parallelShellSort.sort(arr16, 16);
        isSorted(arr16);
    }

    private void isSorted(int[] data){
        for(int i = 1; i < data.length; i++) {
            if (data[i - 1] > data[i]) {
                fail();
            }
        }
    }

}