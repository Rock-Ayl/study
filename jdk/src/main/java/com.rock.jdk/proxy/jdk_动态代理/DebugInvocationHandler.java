package com.rock.jdk.proxy.jdk_动态代理;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 定义 jdk 动态代理类
 *
 * @Author ayl
 * @Date 2025-02-22
 */
public class DebugInvocationHandler implements InvocationHandler {

    //代理的对象
    private final Object target;

    /**
     * 构造方法
     *
     * @param target 被代理的对象
     */
    public DebugInvocationHandler(Object target) {
        this.target = target;
    }

    /**
     * 实现功能的定义
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws InvocationTargetException, IllegalAccessException {
        //调用前
        System.out.println("before method " + method.getName());
        //调用
        Object result = method.invoke(target, args);
        //调用后
        System.out.println("after method " + method.getName());
        //返回
        return result;
    }

}