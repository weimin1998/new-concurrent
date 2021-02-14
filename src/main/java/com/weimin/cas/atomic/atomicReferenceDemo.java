package com.weimin.cas.atomic;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 原子引用
 */
public class atomicReferenceDemo {
    public static void main(String[] args) {

        AccountAtomicReference account = new AccountAtomicReference();
        account.setBalance(new BigDecimal(10000));
        List<Thread> list = new ArrayList<>();

        for (int i = 0; i < 1000; i++) {
            Thread thread = new Thread(()->{
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
    private BigDecimal balance;

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void withdraw(int amount){
        balance = balance.subtract(new BigDecimal(amount));
    }
}

class AccountAtomicReference{
    private AtomicReference<BigDecimal> balance;

    public void setBalance(BigDecimal balance) {
        this.balance = new AtomicReference<>(balance);
    }

    public BigDecimal getBalance() {
        return balance.get();
    }

    public void withdraw(int amount){
        while (true){
            BigDecimal pre = balance.get();
            BigDecimal next = pre.subtract(new BigDecimal(amount));

            if(balance.compareAndSet(pre,next)){
                break;
            }
        }
    }
}
