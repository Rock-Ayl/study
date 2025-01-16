package com.rock.jdk.concurrent.lock.aqs;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * @Author ayl
 * @Date 2025-01-16
 */
public class Mutex {

    /**
     * 实现AQS
     */
    private static class Sync extends AbstractQueuedSynchronizer {

        /**
         * 是否处于占用状态
         *
         * @return
         */
        protected boolean isHeldExclusively() {
            return getState() == 1;
        }

        /**
         * 当状态为0的时候获取锁，CAS操作成功，则state状态为1，
         *
         * @param acquires
         * @return
         */
        public boolean tryAcquire(int acquires) {
            //cas操作
            if (compareAndSetState(0, 1)) {
                setExclusiveOwnerThread(Thread.currentThread());
                //是
                return true;
            }
            //否
            return false;
        }

        /**
         * 释放锁，将同步状态置为0
         *
         * @param releases
         * @return
         */
        protected boolean tryRelease(int releases) {
            //如果已经释放了
            if (getState() == 0) {
                //抛出异常
                throw new IllegalMonitorStateException();
            }
            setExclusiveOwnerThread(null);
            //设置状态
            setState(0);
            //返回
            return true;
        }

    }

    //初始化 同步对象
    private final Sync sync = new Sync();

    /**
     * 获取锁
     */
    public void lock() {
        this.sync.acquire(1);
    }

    /**
     * 尝试获取锁
     *
     * @return
     */
    public boolean tryLock() {
        return this.sync.tryAcquire(1);
    }

    /**
     * 释放锁
     */
    public void unlock() {
        this.sync.release(1);
    }

    /**
     * 判断是否被获取锁了
     *
     * @return
     */
    public boolean isLocked() {
        return this.sync.isHeldExclusively();
    }

}