package com.rock.jdk.proxy.cglib_动态代理;

import net.sf.cglib.proxy.Enhancer;

/**
 * 代理类 工厂
 *
 * @Author ayl
 * @Date 2025-02-22
 */
public class CglibProxyFactory {

    /**
     * 构造代理类、被代理类
     *
     * @param clazz
     * @return
     */
    public static Object getProxy(Class<?> clazz) {
        //创建动态代理增强类
        Enhancer enhancer = new Enhancer();
        //设置类加载器
        enhancer.setClassLoader(clazz.getClassLoader());
        //设置被代理类
        enhancer.setSuperclass(clazz);
        //设置方法拦截器
        enhancer.setCallback(new CglibDebugMethodInterceptor());
        //创建代理类、被代理类
        return enhancer.create();
    }

}