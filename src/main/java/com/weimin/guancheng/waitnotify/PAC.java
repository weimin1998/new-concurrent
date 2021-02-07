package com.weimin.guancheng.waitnotify;

import com.weimin.util.MyUtil;

import java.util.LinkedList;

/**
 * 异步设计模式
 * 生产者消费者
 */
public class PAC {
    public static void main(String[] args) {
        MessageQueue messageQueue = new MessageQueue(3);

        for (int i = 0; i < 4; i++) {
            final int a = i;
            new Thread(()->{
                messageQueue.put(new Message<>(a,"data"+a));
            },"生产者"+a).start();

        }

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        new Thread(()->{
            Message message = messageQueue.get();
            MyUtil.print(message);
        },"消费者").start();
    }
}

class MessageQueue{
    private LinkedList<Message<String>> queue = new LinkedList<>();

    // 消息队列的容量
    private int capacity;

    public MessageQueue(int capacity){
        this.capacity = capacity;
    }

    public void put(Message<String> message){
        synchronized (queue){
            while (queue.size()==capacity){
                MyUtil.print("队列已满");
                try {
                    queue.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            MyUtil.print("生产数据:"+message);
            queue.addLast(message);
            queue.notifyAll();
        }
    }

    public Message get(){
        synchronized (queue){
            while (queue.isEmpty()){
                MyUtil.print("队列已空");
                try {
                    queue.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            Message message = queue.removeFirst();
            queue.notifyAll();
            return message;

        }
    }

}

class Message<T>{
    private int id;
    private T data;

    public Message(int id, T data) {
        this.id = id;
        this.data = data;
    }

    @Override
    public String toString() {
        return "Message{" +
                "id=" + id +
                ", data=" + data +
                '}';
    }
}
