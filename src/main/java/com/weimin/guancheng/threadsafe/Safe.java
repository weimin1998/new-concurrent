package com.weimin.guancheng.threadsafe;

import java.util.ArrayList;
import java.util.List;

/**
 * 引用类型的变量 如果没有暴露给其他线程，也是安全的
 * 暴露给了其他线程就是不安全的
 */
public class Safe {
    public static void main(String[] args) {
        Safe safe = new Unsafe();
        for (int i = 0; i < 200; i++) {
            new Thread(()->{
                safe.m1();
            }).start();
        }
    }
    public void m1(){
        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < 200; i++) {
            m2(list);
            m3(list);
        }
    }

    private void m2(List<Integer> list) {
        list.add(1);
    }
    public void m3(List<Integer> list) {
        list.remove(0);
    }
}


class Unsafe extends Safe{
    @Override
    public void m3(List<Integer> list) {
        new Thread(()->{
            list.remove(0);
        }).start();
    }
}
