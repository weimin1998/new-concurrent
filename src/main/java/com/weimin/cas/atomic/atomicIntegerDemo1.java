package com.weimin.cas.atomic;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.IntUnaryOperator;

/**
 * 原子整数
 * 复杂的api
 *  处理复杂的运算
 */
public class atomicIntegerDemo1 {
    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger(3);

        int i = atomicInteger.updateAndGet(new IntUnaryOperator() {
            @Override
            public int applyAsInt(int operand) {
                return operand * 10;
            }
        });

        System.out.println(i);

    }
}
