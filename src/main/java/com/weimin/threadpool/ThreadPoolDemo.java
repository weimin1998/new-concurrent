package com.weimin.threadpool;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.HashSet;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 自定义线程池
 */
public class ThreadPoolDemo {

    public static void main(String[] args) {
        ThreadPool threadPool =
                new ThreadPool(5,4,1000,TimeUnit.MILLISECONDS,null);
        threadPool.execute(new Runnable() {
            @Override
            public void run() {
                System.out.println("haha");
            }
        });
    }
}

/**
 * 存放任务的集合
 *
 * @param <T>
 */
class BlockingQueue<T> {
    // 一个队列，来存放任务
    private Deque<T> deque = new ArrayDeque<>();

    // 锁
    private Lock lock = new ReentrantLock();

    // 生产者的条件变量
    // 一定会有其他的线程不断地向任务队列里添加任务，当队列满的时候，生产者线程必须等待
    private Condition product = lock.newCondition();
    // 消费者的条件变量
    // 线程池中的线程，相当于消费者 从任务队列取出任务 并执行，当队列空，消费者线程必须等待
    private Condition consume = lock.newCondition();

    // 队列的容量
    private int capacity;

    public BlockingQueue(int capacity) {
        this.capacity = capacity;
    }

    // 从任务队列中取出任务
    // 是阻塞获取的
    public T get() {
        try {
            lock.lock();
            while (deque.isEmpty()) {
                try {
                    consume.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            T first = deque.removeFirst();
            product.signal();
            return first;

        } finally {
            lock.unlock();
        }
    }

    // 带超时的阻塞获取
    // 一段时间获取不到 就算了
    public T get(long timeout, TimeUnit timeUnit) {
        long nanos = timeUnit.toNanos(timeout);
        try {
            lock.lock();
            while (deque.isEmpty()) {
                try {
                    if (nanos <= 0) {
                        return null;
                    }
                    // 返回值 就是还要等多长时间
                    nanos = consume.awaitNanos(nanos);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            T first = deque.removeFirst();
            product.signal();
            return first;

        } finally {
            lock.unlock();
        }
    }

    // 向队列中添加任务
    public void put(T t) {
        try {
            lock.lock();

            while (deque.size() == capacity) {
                try {
                    product.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            deque.addLast(t);
            consume.signal();
        } finally {
            lock.unlock();
        }
    }

    // 带超时的添加,一段时间添加不进去就算了
    // 返回是否添加成功
    public boolean put(T t,long timeout, TimeUnit timeUnit) {
        long nanos = timeUnit.toNanos(timeout);
        try {
            lock.lock();

            while (deque.size() == capacity) {
                try {
                    if(nanos<=0){
                        return false;
                    }
                    nanos = product.awaitNanos(nanos);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            deque.addLast(t);
            consume.signal();
            return true;
        } finally {
            lock.unlock();
        }
    }

    public void tryPut(RejectPolicy<T> rejectPolicy, T runnable) {
        lock.lock();
        try {
            if(deque.size() == capacity){
                // 队列 中任务的数量等于 阈值
                // 具体怎么做，自己实现
                rejectPolicy.reject(this,runnable);

            }else {
                deque.addLast(runnable);
                consume.signal();
            }
        }finally {
            lock.unlock();
        }
    }
}

/**
 * 线程池 存放线程的集合
 */
class ThreadPool {

    // 任务队列
    private BlockingQueue<Runnable> tasks;

    // 存放线程的容器
    // 泛型 不写Thread,是因为包含的信息不够多
    // 要包装一下
    private HashSet<Worker> workers = new HashSet<>();

    // 核心线程数
    private int coreSize;

    // 获取任务的超时时间
    private long timeout;
    private TimeUnit timeUnit;

    // 拒绝策略
    private RejectPolicy<Runnable> rejectPolicy;


    public ThreadPool(int taskQueueCapacity, int coreSize, long timeout, TimeUnit timeUnit,RejectPolicy<Runnable> rejectPolicy) {
        this.tasks = new BlockingQueue<>(taskQueueCapacity);
        this.coreSize = coreSize;
        this.timeout = timeout;
        this.timeUnit = timeUnit;
        this.rejectPolicy = rejectPolicy;
    }

    // 对线程的一次包装
    class Worker extends Thread{
        private Runnable runnable;
        public Worker(Runnable runnable){
            this.runnable = runnable;
        }

        @Override
        public void run() {
            // 如果当前任务不为空，或者任务队列不为空，就执行任务

            while (runnable!=null || (runnable = tasks.get(timeout,timeUnit))!=null){
                try {
                    runnable.run();
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    runnable = null;
                }
            }

            // 所有任务都执行完了，就把当前核心线程移除
            synchronized (workers){
                workers.remove(this);
            }
        }
    }


    // 执行任务
    public void execute(Runnable runnable){
        // 保证workers的线程安全
        synchronized (workers){
            // 如果核心线程的数量还没有超过 阈值
            // 就创建一个新的核心线程去执行任务，并且把它放入核心线程集合
            if(workers.size()<coreSize){
                Worker worker = new Worker(runnable);
                workers.add(worker);
                // 执行
                worker.start();
            }else {
                // 核心线程的数量已经等于阈值了
                // 就需要把任务 加入任务队列

                // 这时就需要考虑 当任务队列满了 拒绝策略

                // 1 等，就嗯等
                //tasks.put(runnable);
                // 2 带超时的等
                tasks.tryPut(rejectPolicy,runnable);
                // 3 放弃
                // 4 调用者自己去执行
                // 5 调用者异常
            }
        }

    }
}

interface RejectPolicy<T>{
    // 你得知道队列的情况，才能决定使用什么策略
    void reject(BlockingQueue<T> blockingQueue,T t);
}
