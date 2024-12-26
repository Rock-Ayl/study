package com.rock.dubbo.dubbo_spi_adaptive_expansion.maker.wheel;

import com.rock.dubbo.dubbo_spi_adaptive_expansion.bo.Wheel;
import org.apache.dubbo.common.extension.SPI;

import java.net.URL;

/**
 * 制作车轮 定义接口
 */
@SPI
public interface WheelMaker {

    /**
     * 制作车轮
     *
     * @param url
     * @return
     */
    Wheel makeWheel(URL url);

}