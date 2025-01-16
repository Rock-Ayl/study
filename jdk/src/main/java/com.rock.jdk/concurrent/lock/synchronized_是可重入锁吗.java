package com.rock.jdk.concurrent.lock;

/**
 * synchronized 是可重入锁吗？
 * 答案：是
 *
 * @Author ayl
 * @Date 2024-12-21
 */
public class synchronized_是可重入锁吗 {

    private synchronized void method1() {
        System.out.println("Inside method1");
        //这里再次调用一个同步方法
        method2();
    }

    private synchronized void method2() {
        System.out.println("Inside method2");
    }

    public static void main(String[] args) {
        synchronized_是可重入锁吗 example = new synchronized_是可重入锁吗();
        example.method1();
    }


}
