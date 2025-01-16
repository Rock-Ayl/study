package com.rock.jdk.concurrent.lock;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

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
    public static class TaskQueue {

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
            //如果为空
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

        for (int i = 0; i < 5; i++) {
            Thread thread = new Thread() {
                public void run() {
                    // 执行task:
                    while (true) {
                        try {
                            String s = task.getTask();
                            System.out.println("execute task: " + s);
                        } catch (InterruptedException e) {
                            return;
                        }
                    }
                }
            };
            thread.start();
            threadList.add(thread);
        }
        var add = new Thread(() -> {
            for (int i = 0; i < 10; i++) {
                // 放入task:
                String s = "t-" + Math.random();
                System.out.println("add task: " + s);
                task.addTask(s);
                try {
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                }
            }
        });
        add.start();
        add.join();
        Thread.sleep(100);
        for (var t : threadList) {
            t.interrupt();
        }
    }

}