package com.rock.jdk.concurrent.lock.Condition使用的demo;

/**
 * 生产者
 * @Author ayl
 * @Date 2025-01-15
 */
public class Producer implements Runnable {

    private final Warehouse warehouse;

    public Producer(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    @Override
    public void run() {
        for (int i = 0; i < 10; i++) {
            try {
                warehouse.put(i);
                Thread.sleep(1000); // 模拟生产过程
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                System.out.println("Producer interrupted");
            }
        }
    }

}
