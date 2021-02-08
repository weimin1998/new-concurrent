package com.weimin.guancheng.jiaoti;

public class MAS {
    public static void main(String[] args) {
        Print35 print35 = new Print35();
        new Thread(()->{
            for (int i = 0; i < 3; i++) {
                print35.printSlaver();
            }
        }).start();


        for (int i = 0; i < 3; i++) {
            print35.printMain();
        }
    }
}

class Print35{
    private boolean flag = false;
    public synchronized void printMain(){
        for (int i = 1; i <=5; i++) {
            while (flag){
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("Main:"+i);
            if(i%5==0){
                flag = true;
                notify();
            }

        }

    }

    public synchronized void printSlaver(){
        for (int i = 1; i <=3; i++) {
            while (!flag){
                try {
                    wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            System.out.println("Slaver:"+i);
            if(i%3==0){
                flag = false;
                notify();
            }
        }
    }
}
