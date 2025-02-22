package com.rock.jdk.proxy.jdk_动态代理;

/**
 * 定义发送接口
 *
 * @Author ayl
 * @Date 2025-02-22
 */
public interface SmsService {

    /**
     * 定义发送
     *
     * @param message
     * @return
     */
    String send(String message);

}