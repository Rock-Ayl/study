package com.rock.jdk.concurrent.lock;

import java.util.*;

/**
 * synchronized_使用 wait 和 notify
 *
 * @Author ayl
 * @Date 2025-01-16
 */
public class synchronized_使用wait和notify {

    /**
     * 任务仓库
     */
    private static class TaskQueue {

        //任务队列
        private Queue<String> queue = new LinkedList<>();

        /**
         * 增加任务
         *
         * @param s
         */
        private synchronized void addTask(String s) {
            //增加任务
            this.queue.add(s);
            //通知所有消费
            this.notifyAll();
        }

        /**
         * 消费任务
         *
         * @return
         * @throws InterruptedException
         */
        private synchronized String getTask() throws InterruptedException {

            /**
             * 这里必须是 while 而不是 if
             * 一个 notifyAll 会唤醒所有线程,但是只有一个库存,剩下的必须重新判断并继续等待
             */
            while (queue.isEmpty()) {
                //等待
                this.wait();
            }
            //消费
            return queue.remove();
        }

    }

    public static void main(String[] args) throws InterruptedException {

        //任务
        TaskQueue task = new TaskQueue();
        //线程列表
        List<Thread> threadList = new ArrayList<>();

        /**
         * 生成 消费者线程 列表
         */

        //循环
        for (int i = 0; i < 5; i++) {
            //初始化消费者线程
            Thread thread = new Thread(Integer.toString(i)) {
                public void run() {
                    //死循环
                    while (true) {
                        try {
                            //获取name
                            String taskName = task.getTask();
                            //输出
                            System.out.println(String.format("线程[%s]->消费[%s]", Thread.currentThread().getName(), taskName));
                            try {
                                Thread.sleep(2500);
                            } catch (InterruptedException e) {
                            }
                        } catch (InterruptedException e) {
                            return;
                        }
                    }
                }
            };
            //启动
            thread.start();
            //加入列表
            threadList.add(thread);
        }

        /**
         * 生成 生产者
         */

        //初始化生产者
        Thread add = new Thread(() -> {
            //循环
            for (int i = 0; i < 100; i++) {
                //放入task:
                String taskName = "任务-" + new Random().nextInt(100);
                //输出
                System.out.println(String.format("线程[%s]->增加[%s]", Thread.currentThread().getName(), taskName));
                //增加任务
                task.addTask(taskName);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                }
            }
        });
        add.setName("生产者");
        add.start();

    }

}