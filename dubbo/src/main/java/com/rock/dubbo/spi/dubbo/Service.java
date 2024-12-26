package com.rock.dubbo.spi.dubbo;

import org.apache.dubbo.common.extension.SPI;

/**
 * dubbo SPI 服务定义
 */
//这个注解是 dubbo spi 的 服务层必要的注解
@SPI
public interface Service {

    /**
     * 定义服务
     */
    void hello();

}