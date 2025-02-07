package com.rock.dubbo.spi.java;

/**
 * Java SPI 实现2
 *
 * @Author ayl
 * @Date 2024-12-25
 */
public class TwoImpl implements Service {

    //时间戳
    private long time = System.currentTimeMillis();

    @Override
    public void hello() {
        System.out.println("java 服务实现2," + this.time);
    }

}
