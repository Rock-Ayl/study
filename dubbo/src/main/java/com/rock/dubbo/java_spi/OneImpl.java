package com.rock.dubbo.java_spi;

/**
 * Java SPI 实现1
 *
 * @Author ayl
 * @Date 2024-12-25
 */
public class OneImpl implements Service {

    @Override
    public void hello() {
        System.out.println("java 服务实现1");
    }

}