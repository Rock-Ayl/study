package com.rock.jdk.concurrent.lock.Condition使用的demo;

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
    public Warehouse(int maxSize) {
        this.maxSize = maxSize;
        this.size = 0;
    }

    /**
     * 存入库存
     *
     * @throws InterruptedException
     */
    public void put() throws InterruptedException {
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
    public void take() throws InterruptedException {
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