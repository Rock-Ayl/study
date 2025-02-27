package com.rock.jdk.concurrent;

import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * jdk 原生、非 Spring 构造器
 *
 * @Author ayl
 * @Date 2025-02-27
 */
public class Jdk_原生线程池_demo {

    public static void main(String[] args) {

        /**
         * 线程池构建
         */

        //创建 jdk 原生线程池
        ThreadPoolExecutor executor = new ThreadPoolExecutor(
                //核心线程数
                5,
                //最大线程数
                10,
                //非核心线程空闲存活时间
                60,
                //存活单位
                TimeUnit.SECONDS,
                //指定阻塞队列,指定为有界阻塞队列
                new LinkedBlockingQueue<>(100),
                //指定线程工厂
                Executors.defaultThreadFactory(),
                //拒绝策略
                new ThreadPoolExecutor.AbortPolicy()
        );

        /**
         * 实现 or 提交 任务
         */

        //提交任务到线程池
        for (int i = 0; i < 20; i++) {
            //任务id
            final int taskId = i;

            //实现任务
            executor.execute(() -> {
                System.out.println("实现任务: " + taskId + "，线程: " + Thread.currentThread().getName());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });

            //提交任务
            executor.submit(() -> {
                System.out.println("提交任务: " + taskId + "，线程: " + Thread.currentThread().getName());
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });

        }
        //关闭线程池（非阻塞）
        executor.shutdown();

    }

}