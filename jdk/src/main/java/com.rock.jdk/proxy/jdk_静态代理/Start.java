package com.rock.jdk.proxy.jdk_静态代理;

/**
 * 启动静态代理调用
 *
 * @Author ayl
 * @Date 2025-02-22
 */
public class Start {

    public static void main(String[] args) {
        //构造被代理的对象
        SmsService smsService = new SmsServiceImpl();
        //代理对象
        SmsProxy smsProxy = new SmsProxy(smsService);
        //代理实现
        smsProxy.send("java");
    }

}
