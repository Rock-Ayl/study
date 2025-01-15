package com.rock.jdk.concurrent.lock.Condition使用的demo;

/**
 * 消费者
 * @Author ayl
 * @Date 2025-01-15
 */

public class Consumer implements Runnable {

    private final Warehouse warehouse;

    public Consumer(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

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