package com.rock.jdk.concurrent.lock.Condition使用的demo;

import java.util.ArrayList;
import java.util.List;

/**
 * 启动模拟
 * -
 * {@link java.util.concurrent.locks.Condition} 本质是 实现了 synchronized 的 wait + notify 的逻辑
 *
 * @Author ayl
 * @Date 2025-01-15
 */
public class Start {

    public synchronized static void main(String[] args) {

        /**
         * 统一仓库
         */

        //创建仓库
        Warehouse warehouse = new Warehouse(5);

        /**
         * 创建 生产者
         */

        //初始化生产者
        Thread producerThread = new Thread(new Runnable() {
            @Override
            public void run() {
                //循环
                for (; ; ) {
                    try {
                        //生产
                        warehouse.put();
                        //等待
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        //当前线程打上 中断标记
                        Thread.currentThread().interrupt();
                        System.out.println("生产者-已经打上中断标记");
                    }
                }
            }
        }, "生产者");

        /**
         * 创建 消费者 列表
         */

        //初始化消费者列表
        List<Thread> consumerThreadList = new ArrayList<>();
        //循环
        for (int i = 0; i < 10; i++) {
            //初始化消费者
            Thread consumerThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    //循环
                    for (int i = 0; i < 10; i++) {
                        try {
                            //消费
                            warehouse.take();
                            //等待
                            Thread.sleep(2000);
                        } catch (InterruptedException e) {
                            //当前线程打上 中断标记
                            Thread.currentThread().interrupt();
                            System.out.println("消费者-已经打上中断标记");
                        }
                    }
                }
            }, "消费者-" + i);
            //加入队列
            consumerThreadList.add(consumerThread);
        }

        /**
         * 启动
         */

        //启动生产者
        producerThread.start();
        //循环
        for (Thread consumerThread : consumerThreadList) {
            //启动消费者
            consumerThread.start();
        }

    }

}
