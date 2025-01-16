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
        //循环
        for (; ; ) {
            try {
                //生产
                this.warehouse.put();
                //等待
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                //当前线程打上 中断标记
                Thread.currentThread().interrupt();
                System.out.println("生产者-已经打上中断标记");
            }
        }
    }

}
