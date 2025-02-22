package com.rock.jdk.proxy.jdk_静态代理;

/**
 * 静态代理类
 *
 * @Author ayl
 * @Date 2025-02-22
 */
public class SmsProxy implements SmsService {

    //代理的对象
    private final SmsService smsService;

    /**
     * 构造方法
     *
     * @param smsService 被代理的对象
     */
    public SmsProxy(SmsService smsService) {
        this.smsService = smsService;
    }

    /**
     * 代理实现
     *
     * @param message 消息
     * @return
     */
    @Override
    public String send(String message) {
        //调用前
        System.out.println("before method send()");
        //调用本身
        String send = smsService.send(message);
        //调用后
        System.out.println("after method send()");
        //返回
        return send;
    }

}