package com.rock.jdk.proxy.cglib_动态代理;

import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * 自定义 MethodInterceptor (方法拦截器)
 *
 * @Author ayl
 * @Date 2025-02-22
 */
public class DebugMethodInterceptor implements MethodInterceptor {

    /**
     * 实现功能的定义
     *
     * @param object      被代理的对象（需要增强的对象）
     * @param method      被拦截的方法（需要增强的方法）
     * @param args        方法入参
     * @param methodProxy 用于调用原始方法
     */
    @Override
    public Object intercept(Object object, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        //调用前
        System.out.println("before method " + method.getName());
        //调用
        Object result = methodProxy.invokeSuper(object, args);
        //调用后
        System.out.println("after method " + method.getName());
        //返回
        return result;
    }

}