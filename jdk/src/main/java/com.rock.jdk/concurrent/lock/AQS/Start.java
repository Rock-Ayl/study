package com.rock.jdk.concurrent.lock.AQS;

import java.util.concurrent.CyclicBarrier;

/**
 * 测试锁
 *
 * @Author ayl
 * @Date 2025-01-16
 */
public class Start {

    //初始化 简单的锁
    private static MyEasyLock myEasyLock = new MyEasyLock();

    //循环篱栅,数量=31
    private static CyclicBarrier barrier = new CyclicBarrier(31);

    //数字
    private static int sum = 0;

    /**
     * 目的：启用30个线程，每个线程+10000次,同步正常的话,最终结果应为300000
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {

        /**
         * 未加锁 实现
         */

        //循环
        for (int i = 0; i < 30; i++) {
            //初始化线程、启动
            new Thread(new Runnable() {
                @Override
                public void run() {
                    //循环1万次
                    for (int i = 0; i < 10000; i++) {
                        //不安全+1
                        unsafeIncr();
                    }
                    try {
                        //-1,等待其他线程结束
                        barrier.await();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
        //-1,等待其他线程结束
        barrier.await();
        //输出结果
        System.out.println("加锁前，sum=" + sum);

        /**
         * 重置
         */

        //重置
        barrier.reset();
        //重置和
        sum = 0;

        /**
         * 加锁 实现
         */

        //循环
        for (int i = 0; i < 30; i++) {
            //初始化线程、启动
            new Thread(new Runnable() {
                @Override
                public void run() {
                    //循环1万次
                    for (int i = 0; i < 10000; i++) {
                        //安全+1
                        safeIncr();
                    }
                    try {
                        //-1,等待其他线程结束
                        barrier.await();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
        //-1,等待其他线程结束
        barrier.await();
        //输出结果
        System.out.println("加锁后，sum=" + sum);

    }

    /**
     * 线程不安全 +1
     */
    private static void unsafeIncr() {
        //+1
        sum++;
    }

    /**
     * 线程安全 +1
     */
    private static void safeIncr() {
        //加锁
        myEasyLock.lock();
        //+1
        sum++;
        //释放锁
        myEasyLock.unlock();
    }

}