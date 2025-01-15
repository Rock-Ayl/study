package com.rock.jdk.concurrent.lock.Condition使用的demo;

/**
 * 启动模拟
 *
 * @Author ayl
 * @Date 2025-01-15
 */
public class Start {

    public static void main(String[] args) {

        Warehouse warehouse = new Warehouse(5);

        Thread producerThread = new Thread(new Producer(warehouse), "Producer");
        Thread consumerThread = new Thread(new Consumer(warehouse), "Consumer");

        producerThread.start();
        consumerThread.start();
    }

}
