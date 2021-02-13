package com.weimin.cas.atomic;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 原子整数
 */
public class atomicIntegerDemo {
    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger();

        System.out.println(atomicInteger.incrementAndGet()); // ++i
        System.out.println(atomicInteger.getAndIncrement()); // i++
        System.out.println(atomicInteger.get());

        System.out.println(atomicInteger.addAndGet(4));

    }
}
