package com.weimin.cas;

import java.util.ArrayList;
import java.util.List;

/**
 * 一个账户有10000元
 * 1000个线程 每个-10
 *
 */
public class Test {
    public static void main(String[] args) {
        Account account = new Account(10000);

        List<Thread> list = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            Thread thread = new Thread(() -> {
                account.withdraw(10);
            });

            list.add(thread);
        }

        for (Thread thread : list) {
            thread.start();
        }

        for (Thread thread : list) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println(account.getBalance());
    }
}

class Account{
    private Integer balance;
    public Account(Integer balance){
        this.balance = balance;
    }

    public  void withdraw(Integer amount){
        this.balance-=amount;
    }

    public Integer getBalance() {
        return balance;
    }
}