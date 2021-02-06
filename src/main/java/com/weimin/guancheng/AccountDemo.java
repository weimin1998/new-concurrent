package com.weimin.guancheng;

import java.util.Random;

/**
 * 转帐
 * 两个账户 两个线程 ，模拟两个用户
 * 分别给对方转1000次帐
 *
 * 最后两个账户的余额之和应该不变
 *
 * 共享变量 是两个账户的总余额
 * 所以对两个账户的操作应该是原子的
 */
public class AccountDemo {
    public static void main(String[] args) {
        Account a1 = new Account(10000);
        Account a2 = new Account(10000);

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                a1.transfer(a2, new Random().nextInt(100) + 1);
            }
        });

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                a2.transfer(a1, new Random().nextInt(100) + 1);
            }
        });

        t1.start();
        t2.start();

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println(a1.getBalance());
        System.out.println(a2.getBalance());
        System.out.println(a1.getBalance()+a2.getBalance());

    }
}

class Account{
    private int balance;

    public Account(int balance){
        this.balance = balance;
    }

    public int getBalance() {
        return balance;
    }

    public  void transfer(Account account, int money){
        synchronized (Account.class){
            if(balance>money){
                balance-=money;
                account.balance+=money;
            }
        }

    }
}
