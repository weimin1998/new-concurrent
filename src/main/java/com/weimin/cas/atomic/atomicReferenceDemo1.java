package com.weimin.cas.atomic;

import com.weimin.util.MyUtil;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 原子引用AtomicReference
 * 不能感知这样的变化： A->B,B->A
 */
public class atomicReferenceDemo1 {
    public static void main(String[] args) {
        AtomicReference<String> atomicReference = new AtomicReference<>("A");

        change(atomicReference);

        // 并不能感知到 其他线程对共享变量的修改
        boolean b = atomicReference.compareAndSet("A", "C");
        MyUtil.print("从A->C:"+b);

    }

    private static void change(AtomicReference<String> atomicReference) {
        Thread t1 = new Thread(()->{
            boolean b = atomicReference.compareAndSet("A", "B");
            MyUtil.print("从A->B:"+b);
        },"t1");
        Thread t2 = new Thread(()->{
            boolean b = atomicReference.compareAndSet("B", "A");
            MyUtil.print("从B->A:"+b);
        },"t2");

        t1.start();
        t2.start();
        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
