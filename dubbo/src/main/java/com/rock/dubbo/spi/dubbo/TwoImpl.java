package com.rock.dubbo.spi.dubbo;

/**
 * dubbo SPI 实现2
 *
 * @Author ayl
 * @Date 2024-12-25
 */
public class TwoImpl implements Service {

    @Override
    public void hello() {
        System.out.println("dubbo 服务实现2");
    }

}