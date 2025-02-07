package com.rock.dubbo.spi.java;

/**
 * Java SPI 实现1
 *
 * @Author ayl
 * @Date 2024-12-25
 */
public class OneImpl implements Service {

    //时间戳
    private long time = System.currentTimeMillis();

    @Override
    public void hello() {
        System.out.println("java 服务实现1," + this.time);
    }

}