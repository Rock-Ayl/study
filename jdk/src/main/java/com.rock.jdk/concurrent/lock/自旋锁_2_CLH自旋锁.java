package com.rock.jdk.concurrent.lock;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicReference;

/**
 * CLH 自旋锁 乐观锁
 *
 * @Author ayl
 * @Date 2025-01-17
 */
public class 自旋锁_2_CLH自旋锁 {

    /**
     * CLH锁节点
     */
    private static class CLHNode {

        /**
         * true = 自旋获取锁中 or 持有锁中
         * false = 未开始获取锁 or 已释放锁
         * -
         * volatile = 修饰保证多线程可见性
         */
        volatile boolean locked = false;

    }

    /**
     * 引用、模拟 链表的最后一个节点
     */
    private final AtomicReference<CLHNode> tailNode;

    /**
     * 当前线程节点的上一个节点
     */
    private final ThreadLocal<CLHNode> predNode;

    /**
     * 当前线程的节点
     */
    private final ThreadLocal<CLHNode> curNode;

    /**
     * 初始化
     */
    public 自旋锁_2_CLH自旋锁() {
        //全局引用
        this.tailNode = new AtomicReference<>(new CLHNode());
        //初始化，每个线程都初始化自己的节点
        this.curNode = ThreadLocal.withInitial(CLHNode::new);
        //初始化，每个线程都不会有前置节点
        this.predNode = new ThreadLocal<>();
    }

    /**
     * 获取锁
     */
    public void lock() {

        /**
         * 获取当前节点、修改状态
         */

        //获取当前线程的当前节点
        CLHNode currNode = this.curNode.get();
        //修改为 获取锁中 or 已获取到锁，之后就不改了
        currNode.locked = true;

        /**
         * 将当前节点插入到链表尾部
         */

        //获取前置节点，并修改当前节点为最后一个节点
        CLHNode preNode = this.tailNode.getAndSet(currNode);
        //记录其前置节点
        this.predNode.set(preNode);

        /**
         * 自旋获取锁，如果前一个节点 还是true 说明前一个节点还没有：释放锁 or 获取到锁
         */

        //自旋,失败就继续，成功则跳出
        while (preNode.locked == true) {
            //没有获取到锁
            System.out.println("线程" + Thread.currentThread().getName() + "没能获取到锁，进行自旋等待。。。");
        }

        /**
         * 到了这里，说明前一个节点 locked == false 了，该节点视为获取到锁
         */

        //获取到锁
        System.out.println("线程" + Thread.currentThread().getName() + "获取到了锁！！！");
    }

    /**
     * 释放锁
     */
    public void unLock() {

        /**
         * 获取当前节点、修改状态
         */

        //获取当前线程节点
        CLHNode node = curNode.get();
        //修改状态为 已释放锁
        node.locked = false;

        /**
         * 到了这里，该节点已经 释放了锁，其下一个节点应该获取到了锁了
         */

        System.out.println("线程" + Thread.currentThread().getName() + "释放了锁！！！");

        /**
         * 后续处理
         */

        // 小伙伴们可以思考下，下面两句代码的作用是什么？？
        CLHNode newCurNode = new CLHNode();
        curNode.set(newCurNode);

        // 【优化】能提高GC效率和节省内存空间，请思考：这是为什么？
        // curNode.set(predNode.get());

    }

    //自增数字对象
    private static Integer count = 0;

    /**
     * 使用锁的场景
     *
     * @param args
     */
    public static void main(String[] args) throws InterruptedException {

        /**
         * 通过修改配置 控制用例数量
         */

        //线程、自增数
        int size = 100;

        /**
         * 实现自增 (不加锁会导致没有原子性)
         */

        //初始化 CLH自旋锁
        自旋锁_2_CLH自旋锁 lock = new 自旋锁_2_CLH自旋锁();
        //计数器
        CountDownLatch countDownLatch = new CountDownLatch(size);
        //线程数组
        List<Thread> threadList = new ArrayList<>();
        //循环
        for (int i = 0; i < size; i++) {
            //初始化线程，组装到数组
            threadList.add(new Thread(() -> {
                //获取锁
                lock.lock();
                //+1
                count++;
                //释放锁
                lock.unLock();
                //完成，计数器-1
                countDownLatch.countDown();
            }, String.valueOf(i)));
        }

        /**
         * 统一启动线程、结束后输出
         */

        //循环
        for (Thread thread : threadList) {
            //统一启动
            thread.start();
        }
        //等待所有线程结束
        countDownLatch.await();
        System.out.println(count);

    }

}