package com.rock.jdk.lock;

import java.util.concurrent.SynchronousQueue;

/**
 * SynchronousQueue 使用场景，必须一个 放 一个拿
 * 可以这么理解：股票买卖，必须同时有卖家+买家
 *
 * @Author ayl
 * @Date 2024-12-21
 */
public class SynchronousQueue_使用场景 {

    public static void main(String[] args) {

        SynchronousQueue<String> queue = new SynchronousQueue<>();

        // 生产者线程
        Thread producer = new Thread(() -> {
            try {
                System.out.println("生产中...");
                queue.put("Item1");
                System.out.println("生产成功");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        // 消费者线程
        Thread consumer = new Thread(() -> {
            try {
                System.out.println("消费中...");
                String item = queue.take();
                System.out.println("消费成功 " + item);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });

        producer.start();
        consumer.start();
    }
}
