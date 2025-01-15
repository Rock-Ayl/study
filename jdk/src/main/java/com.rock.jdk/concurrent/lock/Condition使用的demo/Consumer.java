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
        for (int i = 0; i < 10; i++) {
            try {
                warehouse.take();
                Thread.sleep(1500); // 模拟消费过程
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Consumer interrupted");
            }
        }
    }

}