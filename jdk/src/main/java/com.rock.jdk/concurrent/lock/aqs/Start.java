package com.rock.jdk.concurrent.lock.aqs;

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

        //未加锁前
        for (int i = 0; i < 30; i++) {
            Thread t = new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < 10000; i++) {
                        increment1();//没有同步措施的a++；
                    }
                    try {
                        barrier.await();//等30个线程累加完毕
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });
            t.start();
        }
        barrier.await();
        System.out.println("加锁前，sum=" + sum);

        /**
         * 加锁 实现
         */

        //加锁后
        barrier.reset();//重置CyclicBarrier
        sum = 0;
        for (int i = 0; i < 30; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    for (int i = 0; i < 10000; i++) {
                        increment2();//a++采用Mutex进行同步处理
                    }
                    try {
                        barrier.await();//等30个线程累加完毕
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
        barrier.await();
        System.out.println("加锁后，sum=" + sum);
    }

    /**
     * 线程不安全 +1
     */
    private static void increment1() {
        //+1
        sum++;
    }

    /**
     * 线程安全 +1
     */
    private static void increment2() {
        //加锁
        myEasyLock.lock();
        //+1
        sum++;
        //释放锁
        myEasyLock.unlock();
    }

}