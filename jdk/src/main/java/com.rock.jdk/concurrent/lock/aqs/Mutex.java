package com.rock.jdk.concurrent.lock.aqs;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * @Author ayl
 * @Date 2025-01-16
 */
public class Mutex {

    /**
     * 实现AQS
     * -
     * 状态枚举: [0=释放][1=锁定]
     */
    private static class Sync extends AbstractQueuedSynchronizer {

        /**
         * 是否处于锁定状态
         *
         * @return
         */
        protected boolean isHeldExclusively() {
            //判断并返回
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
                //记录成功锁定的线程
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
            //取消锁定的线程
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
     * 是否处于锁定状态
     *
     * @return
     */
    public boolean isLocked() {
        return this.sync.isHeldExclusively();
    }

}