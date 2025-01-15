package com.rock.jdk.concurrent.lock.Condition使用的demo;

/**
 * 消费者
 *
 * @Author ayl
 * @Date 2025-01-15
 */

public class Consumer implements Runnable {

    //绑定的仓库
    private final Warehouse warehouse;

    /**
     * 初始化
     *
     * @param warehouse 绑定的仓库
     */
    public Consumer(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    /**
     * 消费实现
     */
    @Override
    public void run() {
        //循环
        for (int i = 0; i < 10; i++) {
            try {
                //消费
                warehouse.take();
                //等待
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                //当前线程打上 中断标记
                Thread.currentThread().interrupt();
                System.out.println("消费者-已经打上中断标记");
            }
        }
    }

}