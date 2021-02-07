package com.weimin.guancheng.waitnotify;

import com.weimin.util.MyUtil;

/**
 * 同步设计模式
 * 保护性暂停
 */
public class Guard {
    public static void main(String[] args) {
        GuardedObject<Integer> guardedObject = new GuardedObject<>();
        new Thread(()->{
            Integer integer = guardedObject.get(2000);
            MyUtil.print("获取到了："+integer);
        }).start();

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(()->{
            guardedObject.set(100);
            MyUtil.print("设置好了");
        }).start();
    }
}

class GuardedObject<T>{

    private T response;


    public synchronized T get(){
        MyUtil.print("开始获取结果");
        while (response==null){
            try {
                this.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }


        return response;
    }

    public synchronized T get(long time){
        MyUtil.print("开始获取结果");

        // 开始等待的时间
        long begin = System.currentTimeMillis();
        // 经过的时间
        long passtime = 0;
        while (response==null){
            // 已经等够时间了
            if(passtime>=time){
                break;
            }
            try {
                this.wait(time-passtime);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            passtime = System.currentTimeMillis()-begin;
        }


        return response;
    }

    public synchronized void set(T t){
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        this.response=t;
        this.notifyAll();
    }
}