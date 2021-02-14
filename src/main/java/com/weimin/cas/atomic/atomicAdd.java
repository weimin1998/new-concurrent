package com.weimin.cas.atomic;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.LongAdder;

/**
 * 原子累加器
 *  效率比AtomicLong 的自增 高
 */
public class atomicAdd {
    public static void main(String[] args) {
        List<Thread> list1 = new ArrayList<>();
        List<Thread> list2 = new ArrayList<>();
        LongAdder longAdder = new LongAdder();
        for (int i = 0; i < 4; i++) {
            Thread thread = new Thread(() -> {
                for (int j = 0; j < 1000000; j++) {
                    longAdder.increment();
                }
            });
            list1.add(thread);
        }
        long start = System.currentTimeMillis();
        for (Thread thread : list1) {
            thread.start();
        }
        for (Thread thread : list1) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("累加器："+(end - start));
        System.out.println(longAdder.sum());

        AtomicLong atomicLong = new AtomicLong();

        for (int i = 0; i < 4; i++) {
            Thread thread = new Thread(() -> {
                for (int j = 0; j < 1000000; j++) {
                    atomicLong.getAndIncrement();
                }
            });
            list2.add(thread);

        }

        long a = System.currentTimeMillis();
        for (Thread thread : list2) {
            thread.start();
        }
        for (Thread thread : list2) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        long b = System.currentTimeMillis();
        System.out.println("long:"+(b-a));
        System.out.println(atomicLong.longValue());


        /*累加器：24
        4000000
        long:87
        4000000*/
    }
}
