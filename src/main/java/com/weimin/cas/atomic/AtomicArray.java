package com.weimin.cas.atomic;

import java.util.Arrays;
import java.util.concurrent.atomic.AtomicIntegerArray;

/**
 * 原子数组
 */
public class AtomicArray {
    public static void main(String[] args) {
        int[] array = {1,2,3,4,5,6,7,8,9,10};
        AtomicIntegerArray atomicIntegerArray = new AtomicIntegerArray(array);

        boolean b = atomicIntegerArray.compareAndSet(3, 4, 11);
        System.out.println(b);
        //
        System.out.println(Arrays.toString(array));
        System.out.println(atomicIntegerArray.length());

        for (int i = 0; i < atomicIntegerArray.length(); i++) {
            System.out.println(atomicIntegerArray.get(i));
        }
    }
}
