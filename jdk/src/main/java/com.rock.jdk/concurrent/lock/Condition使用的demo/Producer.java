package com.rock.jdk.concurrent.lock.Condition使用的demo;

/**
 * 生产者
 *
 * @Author ayl
 * @Date 2025-01-15
 */
public class Producer implements Runnable {

    //绑定的仓库
    private final Warehouse warehouse;

    /**
     * 初始化
     *
     * @param warehouse 昂库
     */
    public Producer(Warehouse warehouse) {
        this.warehouse = warehouse;
    }

    /**
     * 生产实现
     */
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
