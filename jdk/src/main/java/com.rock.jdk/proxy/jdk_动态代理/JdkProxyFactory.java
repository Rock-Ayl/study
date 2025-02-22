package com.rock.jdk.proxy.jdk_动态代理;

import java.lang.reflect.Proxy;

/**
 * 定义 jdk 代理工厂
 *
 * @Author ayl
 * @Date 2025-02-22
 */
public class JdkProxyFactory {

    /**
     * 工厂构造被代理的对象的统一方法
     *
     * @param target
     * @return
     */
    public static Object getProxy(Object target) {
        //根据对象信息、构造对象实例
        return Proxy.newProxyInstance(
                //指定目标类的类加载器
                target.getClass().getClassLoader(),
                //指定代理需要实现的接口,可指定多个
                target.getClass().getInterfaces(),
                //指定对应 InvocationHandler
                new DebugInvocationHandler(target)
        );
    }

}