package com.weimin.cas.atomic;

import com.weimin.util.MyUtil;

import java.util.concurrent.atomic.AtomicMarkableReference;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * 原子引用AtomicMarkableReference
 *
 */
public class atomicReferenceDemo3 {
    public static void main(String[] args) {
        AtomicMarkableReference<String> atomicMarkableReference = new AtomicMarkableReference<>("A",true);

        String reference = atomicMarkableReference.getReference();
        boolean marked = atomicMarkableReference.isMarked();
        System.out.println(marked);
        change(atomicMarkableReference);
        boolean b = atomicMarkableReference.compareAndSet(reference,"C",marked,false);


        MyUtil.print("从A->C:"+b);

    }

    private static void change(AtomicMarkableReference<String> atomicMarkableReference) {
        Thread t1 = new Thread(()->{
            String reference = atomicMarkableReference.getReference();
            boolean marked = atomicMarkableReference.isMarked();

            boolean b = atomicMarkableReference.compareAndSet(reference,"B",marked,false);
            MyUtil.print("从A->B:"+b);
        },"t1");
        Thread t2 = new Thread(()->{
            String reference = atomicMarkableReference.getReference();
            boolean marked = atomicMarkableReference.isMarked();

            boolean b = atomicMarkableReference.compareAndSet(reference,"A",marked,false);
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
