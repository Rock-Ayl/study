package com.rock.jdk.concurrent.lock;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Condition_使用的demo
 * -
 * {@link java.util.concurrent.locks.Condition} 本质是 实现了 synchronized 的 wait + notify 的逻辑
 *
 * @Author ayl
 * @Date 2025-01-15
 */
public class Condition_使用的demo {

    /**
     * 仓库
     *
     * @Author ayl
     * @Date 2025-01-15
     */
    private static class Warehouse {

        //仓库最大库存数量
        private final int maxSize;
        //当前库存数量
        private int size;

        //全局锁
        private final Lock lock = new ReentrantLock(true);

        //条件
        private final Condition notEmpty = this.lock.newCondition();

        /**
         * 初始化仓库
         *
         * @param maxSize 指定最大数量
         */
        private Warehouse(int maxSize) {
            this.maxSize = maxSize;
            this.size = 0;
        }

        /**
         * 存入库存
         */
        private void put() {
            try {
                //获取全局锁
                this.lock.lock();
                //如果仓库满了,过
                if (this.size == this.maxSize) {
                    //过
                    return;
                }
                //添加库存、输出
                System.out.println(String.format("线程[%s]存入库存，当前库存数量=%s", Thread.currentThread().getName(), ++this.size));
                //通知所有消费者
                this.notEmpty.signalAll();
            } finally {
                //释放锁
                this.lock.unlock();
            }
        }

        /**
         * 扣减库存
         *
         * @throws InterruptedException
         */
        private void take() throws InterruptedException {
            try {
                //获取全局锁
                this.lock.lock();

                /**
                 * 如果仓库为空,等待直到有物品可用,必须是if,因为后面还有多个被唤醒的其他消费者在排队
                 */
                while (this.size == 0) {
                    //等待
                    this.notEmpty.await();
                }

                //扣减库存、输出
                System.out.println(String.format("线程[%s]扣减库存，当前库存数量=%s", Thread.currentThread().getName(), --this.size));
            } finally {
                //释放锁
                this.lock.unlock();
            }
        }

    }

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
