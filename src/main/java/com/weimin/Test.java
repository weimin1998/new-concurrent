package com.weimin;

import com.weimin.util.MyUtil;

import java.util.concurrent.TimeUnit;

public class Test {
    public static void main(String[] args) {
        while (true){
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
