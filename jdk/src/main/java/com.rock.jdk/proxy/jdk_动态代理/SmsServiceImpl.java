package com.rock.jdk.proxy.jdk_动态代理;

/**
 * 实现发送
 *
 * @Author ayl
 * @Date 2025-02-22
 */
public class SmsServiceImpl implements SmsService {

    /**
     * 实现发送方法
     *
     * @param message 消息内容
     * @return
     */
    public String send(String message) {
        //out
        System.out.println("send message:" + message);
        //返回
        return message;
    }

}