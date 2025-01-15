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
    private final Lock lock = new ReentrantLock();

    private final Condition notFull = lock.newCondition();
    private final Condition notEmpty = lock.newCondition();

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
        //获取全局锁
        this.lock.lock();
        try {
            //如果仓库已满,等待直到有空间可用
            while (this.size == this.maxSize) {
                //?
                this.notFull.await();
            }
            //添加库存、输出
            System.out.println(String.format("存入库存，当前库存数量=%s", ++this.size));
            //?
            this.notEmpty.signal();
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
        //获取全局锁
        this.lock.lock();
        try {
            //如果仓库为空,等待直到有物品可用
            while (this.size == 0) {
                //?
                this.notEmpty.await();
            }
            //扣减库存、输出
            System.out.println(String.format("扣减库存，当前库存数量=%s", --this.size));
            //?
            this.notFull.signal();
        } finally {
            //释放锁
            this.lock.unlock();
        }
    }

}