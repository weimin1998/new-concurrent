package com.weimin.cas;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 一个账户有10000元
 * 1000个线程 每个-10
 * 不使用锁，使用原子整型
 */
public class Test1 {
    public static void main(String[] args) {
        AccountCAS account = new AccountCAS(10000);

        List<Thread> list = new ArrayList<>(1000);
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

class AccountCAS{
    private AtomicInteger balance;
    public AccountCAS(Integer balance){
        this.balance = new AtomicInteger(balance);
    }

    public void withdraw(Integer amount){
        //
        /*while (true){
            int pre = balance.get();

            int next = pre-amount;

            // cas是原子的
            boolean success = balance.compareAndSet(pre, next);
            if(success){
                break;
            }
        }*/

        // 这一行可以代替上面的
        balance.getAndAdd(-1*amount);
    }

    public Integer getBalance() {
        return balance.get();
    }
}