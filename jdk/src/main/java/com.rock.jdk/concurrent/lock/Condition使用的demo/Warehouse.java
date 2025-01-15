package com.rock.jdk.concurrent.lock.Condition使用的demo;

import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 仓库
 *
 * @Author ayl
 * @Date 2025-01-15
 */
public class Warehouse {

    private final int maxSize;
    private int size;
    private final Lock lock = new ReentrantLock();
    private final Condition notFull = lock.newCondition();
    private final Condition notEmpty = lock.newCondition();

    public Warehouse(int maxSize) {
        this.maxSize = maxSize;
        this.size = 0;
    }

    public void put(int item) throws InterruptedException {
        lock.lock();
        try {
            // 如果仓库已满，等待直到有空间可用
            while (size == maxSize) {
                notFull.await();
            }
            // 添加物品
            size++;
            System.out.println("Produced item: " + item);
            // 唤醒等待的消费者
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    public int take() throws InterruptedException {
        lock.lock();
        try {
            // 如果仓库为空，等待直到有物品可用
            while (size == 0) {
                notEmpty.await();
            }
            // 移除物品
            size--;
            int item = ThreadLocalRandom.current().nextInt(100);
            System.out.println("Consumed item: " + item);
            // 唤醒等待的生产者
            notFull.signal();
            return item;
        } finally {
            lock.unlock();
        }
    }
}