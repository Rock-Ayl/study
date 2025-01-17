package com.rock.jdk.concurrent.lock;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * CAS 自旋锁 乐观锁
 *
 * @Author ayl
 * @Date 2025-01-17
 */
public class 自旋锁_1_CAS自旋锁 {

    //并发包-原子类,默认值=5
    private AtomicInteger number = new AtomicInteger(0);

    /**
     * cas自旋自增+1
     */
    private void incr(CountDownLatch countDownLatch) {
        //获取旧数字
        int oldNumber = number.get();
        //计算新数字
        int newNumber = oldNumber + 1;

        /**
         * 核心逻辑,如果失败,就死循环再来
         */

        //cas自旋，不断尝试，失败则继续尝试
        while (this.number.compareAndSet(oldNumber, newNumber) == false) {
            //重新获取旧数字
            oldNumber = number.get();
            //重新计算新数字
            newNumber = oldNumber + 1;
        }
        //完成,计数器-1
        countDownLatch.countDown();
    }

    public static void main(String[] args) throws InterruptedException {
        //目标线程数、以及自增数量
        int size = 100;
        //初始化
        自旋锁_1_CAS自旋锁 自旋锁_cas自旋锁 = new 自旋锁_1_CAS自旋锁();
        //计数器
        CountDownLatch countDownLatch = new CountDownLatch(size);
        //循环
        for (int i = 0; i < size; i++) {
            //初始化线程,启动
            new Thread(new Runnable() {
                @Override
                public void run() {
                    //大家一起并发自增+1
                    自旋锁_cas自旋锁.incr(countDownLatch);
                }
            }, String.valueOf(i)).start();
        }
        //等待所有线程执行完成
        countDownLatch.await();
        //输出结果
        System.out.println(自旋锁_cas自旋锁.number.get());
    }

}
