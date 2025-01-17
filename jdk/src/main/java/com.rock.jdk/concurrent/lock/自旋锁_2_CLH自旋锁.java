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
         * 该节点是否获取到锁,默认false
         * -
         * volatile 修饰保证多线程可见性
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
        this.tailNode = new AtomicReference<>(new CLHNode());
        this.curNode = ThreadLocal.withInitial(CLHNode::new);
        this.predNode = new ThreadLocal<>();
    }

    /**
     * 获取锁
     */
    public void lock() {
        // 取出当前线程ThreadLocal存储的当前节点，初始化值总是一个新建的CLHNode，locked状态为false。
        CLHNode currNode = curNode.get();
        // 此时把lock状态置为true，表示一个有效状态，
        // 即获取到了锁或正在等待锁的状态
        currNode.locked = true;
        // 当一个线程到来时，总是将尾结点取出来赋值给当前线程的前继节点；
        // 然后再把当前线程的当前节点赋值给尾节点
        // 【注意】在多线程并发情况下，这里通过AtomicReference类能防止并发问题
        // 【注意】哪个线程先执行到这里就会先执行predNode.set(preNode);语句，因此构建了一条逻辑线程等待链
        // 这条链避免了线程饥饿现象发生
        CLHNode preNode = tailNode.getAndSet(currNode);
        // 将刚获取的尾结点（前一线程的当前节点）付给当前线程的前继节点ThreadLocal
        // 【思考】这句代码也可以去掉吗，如果去掉有影响吗？
        predNode.set(preNode);
        // 【1】若前继节点的locked状态为false，则表示获取到了锁，不用自旋等待；
        // 【2】若前继节点的locked状态为true，则表示前一线程获取到了锁或者正在等待，自旋等待
        while (preNode.locked) {
            System.out.println("线程" + Thread.currentThread().getName() + "没能获取到锁，进行自旋等待。。。");
        }
        // 能执行到这里，说明当前线程获取到了锁
        System.out.println("线程" + Thread.currentThread().getName() + "获取到了锁！！！");
    }

    /**
     * 释放锁
     */
    public void unLock() {
        // 获取当前线程的当前节点
        CLHNode node = curNode.get();
        // 进行解锁操作
        // 这里将locked至为false，此时执行了lock方法正在自旋等待的后继节点将会获取到锁
        // 【注意】而不是所有正在自旋等待的线程去并发竞争锁
        node.locked = false;
        System.out.println("线程" + Thread.currentThread().getName() + "释放了锁！！！");
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
            }));
        }
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
