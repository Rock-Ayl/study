package com.rock.jdk.proxy.cglib_动态代理;

/**
 * 调用 cglib 动态代理
 *
 * @Author ayl
 * @Date 2025-02-22
 */
public class Start {

    public static void main(String[] args) {
        //通过工厂构造、代理类、被代理类
        AliSmsService aliSmsService = (AliSmsService) CglibProxyFactory.getProxy(AliSmsService.class);
        //调用代理类
        aliSmsService.send("java");
    }

}
