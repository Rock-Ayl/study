package com.rock.jdk.concurrent.lock.Condition使用的demo;

/**
 * 启动模拟
 *
 * @Author ayl
 * @Date 2025-01-15
 */
public class Start {

    public static void main(String[] args) {

        //创建仓库
        Warehouse warehouse = new Warehouse(3);

        //创建 生产者、消费者、线程，并绑定仓库
        Thread producerThread = new Thread(new Producer(warehouse), "生产者");
        Thread consumerThread = new Thread(new Consumer(warehouse), "消费者");

        //启动
        producerThread.start();
        consumerThread.start();

    }

}
