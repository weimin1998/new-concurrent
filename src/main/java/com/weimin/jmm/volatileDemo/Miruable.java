package com.weimin.jmm.volatileDemo;

import java.util.concurrent.TimeUnit;

public class Miruable {
    volatile static boolean run = true;

    public static void main(String[] args) {
        new Thread(()->{
            while (run){
                //System.out.println();
            }
        }).start();


        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        run = false;
    }
}
