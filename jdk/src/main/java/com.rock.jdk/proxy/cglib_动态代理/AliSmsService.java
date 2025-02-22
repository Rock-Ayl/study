package com.rock.jdk.proxy.cglib_动态代理;

/**
 * 实现发送,不需要定义接口了
 *
 * @Author ayl
 * @Date 2025-02-22
 */
public class AliSmsService {

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