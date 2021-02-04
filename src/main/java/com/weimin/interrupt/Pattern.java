package com.weimin.interrupt;

/**
 * 设计模式
 * 两阶段终止
 * 优雅的终止一个线程
 * 要终止一个线程，不要直接stop(),因为这个线程可能还有事情没有完成，比如还有锁没有释放。。
 *             也不要System.exit(), 因为这样直接结束进程了
 */
public class Pattern {
    public static void main(String[] args) {

    }
}
