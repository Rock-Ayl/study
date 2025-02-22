package com.rock.jdk.proxy.jdk_动态代理;

/**
 * 调用 jdk 动态代理
 *
 * @Author ayl
 * @Date 2025-02-22
 */
public class Start {

    public static void main(String[] args) {
        //初始化【被代理对象】、用工厂构造出【代理对象】
        SmsService smsService = (SmsService) JdkProxyFactory.getProxy(new SmsServiceImpl());
        //调用【代理对象】
        smsService.send("java");
    }

}
