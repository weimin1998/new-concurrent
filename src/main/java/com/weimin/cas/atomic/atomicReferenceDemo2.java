package com.weimin.cas.atomic;

import com.weimin.util.MyUtil;

import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * 原子引用AtomicStampedReference
 * 可以通过版本号，感知这样的变化：A->B,B->A
 * 当然也可以通过版本号感知变化了几次
 */
public class atomicReferenceDemo2 {
    public static void main(String[] args) {
        AtomicStampedReference<String> atomicStampedReference = new AtomicStampedReference<>("A",0);


        // 获取原子引用保护的引用
        String reference = atomicStampedReference.getReference();
        // 获取版本号
        int stamp = atomicStampedReference.getStamp();
        change(atomicStampedReference);
        boolean b = atomicStampedReference.compareAndSet(reference, "C", stamp, stamp+1);
        MyUtil.print("从A->C:"+b);

    }

    private static void change(AtomicStampedReference<String> atomicStampedReference) {
        Thread t1 = new Thread(()->{
            String reference = atomicStampedReference.getReference();
            // 获取版本号
            int stamp = atomicStampedReference.getStamp();
            boolean b = atomicStampedReference.compareAndSet(reference, "B", stamp, stamp+1);
            MyUtil.print("从A->B:"+b);
        },"t1");
        Thread t2 = new Thread(()->{
            String reference = atomicStampedReference.getReference();
            // 获取版本号
            int stamp = atomicStampedReference.getStamp();
            boolean b = atomicStampedReference.compareAndSet(reference, "A", stamp, stamp+1);
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
