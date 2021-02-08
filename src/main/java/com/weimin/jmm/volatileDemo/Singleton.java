package com.weimin.jmm.volatileDemo;

/**
 * 单例
 */
public class Singleton {
    private Singleton(){}

    private volatile static Singleton instance;

    public static Singleton getInstance() {
        if(instance==null){
            synchronized (Singleton.class){
                if(instance==null){
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }

    public static void main(String[] args) {

    }
}
