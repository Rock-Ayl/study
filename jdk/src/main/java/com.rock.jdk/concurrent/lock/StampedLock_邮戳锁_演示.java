package com.rock.jdk.concurrent.lock;

import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.concurrent.locks.StampedLock;


/**
 * 邮戳锁
 * {@link StampedLock} = {@link ReentrantReadWriteLock} + 读的过程中允许写锁介入 + 不可重入
 *
 * @Author ayl
 * @Date 2025-01-07
 */
public class StampedLock_邮戳锁_演示 {

    //被修改、读取 的数字
    private static int number = 1;

    //邮戳锁
    private static StampedLock stampedLock = new StampedLock();

    /**
     * 悲观写
     */
    private void write() {
        //获取写锁
        long stamp = stampedLock.writeLock();
        System.out.println(Thread.currentThread().getName() + "\t写线程-开始修改");
        try {
            //修改
            number++;
        } finally {
            //释放写锁
            stampedLock.unlockWrite(stamp);
        }
        System.out.println(Thread.currentThread().getName() + "\t写线程-结束修改");
    }

    /**
     * 悲观读,读的过程中不允许写锁进入
     */
    private void gloomyRead() throws InterruptedException {
        //获取悲观读锁
        long stamp = stampedLock.readLock();
        System.out.println("悲观读-开始读取");
        for (int i = 0; i < 4; i++) {
            Thread.sleep(1000);
            System.out.println(Thread.currentThread().getName() + "\t悲观读-正在读取中");
        }
        try {
            int result = number;
            System.out.println("悲观读-读取完成，结果：" + result);
            System.out.println("悲观读-写线程没有修改成功，读锁时候写锁无法介入，传统的读写互斥");
        } finally {
            //释放读锁
            stampedLock.unlockRead(stamp);
        }
    }

    /**
     * 乐观读，读的过程中允许写锁进入
     */
    private void optimismRead() throws InterruptedException {

        //获取 乐观读锁
        long stamp = stampedLock.tryOptimisticRead();
        //获取目前乐观的内容
        int result = number;

        /**
         * 乐观读锁,第一次获取,是没被修改的输出
         */

        System.out.println(Thread.currentThread().getName() + "stampedLock.validate()方法：true-无修改 false-有修改");
        System.out.println(Thread.currentThread().getName() + "stampedLock.validate()=" + stampedLock.validate(stamp));

        /**
         * 这期间、写锁介入,内容被修改
         */

        //模拟长时间读取操作
        for (int i = 0; i < 4; i++) {
            Thread.sleep(1000);
            System.out.println(Thread.currentThread().getName() + "stampedLock.validate()=" + stampedLock.validate(stamp));
        }

        /**
         * 最终裁决是否变为悲观模式
         */

        //在读取过程中其他线程修改了数据
        if (stampedLock.validate(stamp) == false) {
            System.out.println("在读取过程中其他线程修改了数据");
            //升级为悲观读锁
            stamp = stampedLock.readLock();
            try {
                System.out.println("乐观锁升级为悲观锁");
                //获取当前读取内容
                result = number;
                System.out.println("悲观锁读取后result=" + result);
            } finally {
                //最终释放读锁
                stampedLock.unlockRead(stamp);
            }
        }

        /**
         * 输出最终读取内容
         */

        System.out.println(Thread.currentThread().getName() + "\tvalue=" + result);
    }

    public static void main(String[] args) throws InterruptedException {

        //初始化对象
        StampedLock_邮戳锁_演示 stampedLock演示 = new StampedLock_邮戳锁_演示();

        /**
         * 读锁
         */

        new Thread(() -> {
            try {

                //悲观读
                //stampedLockDemoe.gloomyRead();

                //乐观读
                stampedLock演示.optimismRead();

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }, "readThread").start();

        /**
         * 等待读锁第一次尝试获取=true
         */

        Thread.sleep(1000);

        /**
         * 写锁
         */

        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "\t写线程开始工作");
            stampedLock演示.write();
        }, "writeThread").start();

        /**
         * 读取持续读取=false
         */

        Thread.sleep(4000);

        /**
         * 结束
         */

        System.out.println(Thread.currentThread().getName() + "\tnumber=" + number);
    }

}
