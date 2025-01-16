package com.rock.jdk.concurrent.lock.aqs;

import java.util.concurrent.CyclicBarrier;

/**
 * @Author ayl
 * @Date 2025-01-16
 */
public class TestMutex {

    //初始化 Mutex
    private static Mutex mutex = new Mutex();

    //循环篱栅,数量=31
    private static CyclicBarrier barrier = new CyclicBarrier(31);

    //数字
    private static int number = 0;

    /**
     * 执行
     *
     * @param args
     * @throws Exception
     */
    public static void main(String[] args) throws Exception {
        //说明:我们启用30个线程，每个线程对i自加10000次，同步正常的话，最终结果应为300000；
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
        System.out.println("加锁前，number=" + number);

        //加锁后
        barrier.reset();//重置CyclicBarrier
        number = 0;
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
        System.out.println("加锁后，number=" + number);
    }

    /**
     * 线程不安全 +1
     */
    public static void increment1() {
        //+1
        number++;
    }

    /**
     * 线程安全 +1
     */
    public static void increment2() {
        //加锁
        mutex.lock();
        //+1
        number++;
        //释放锁
        mutex.unlock();
    }
}