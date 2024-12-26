package com.rock.dubbo.dubbo_spi_adaptive_expansion.maker.car;

import com.rock.dubbo.dubbo_spi_adaptive_expansion.bo.Car;
import org.apache.dubbo.common.extension.SPI;

import java.net.URL;

/**
 * 制造汽车 接口
 *
 * @Author ayl
 * @Date 2024-12-26
 */
@SPI
public interface CarMaker {

    /**
     * 制造汽车
     *
     * @param url
     * @return
     */
    Car makeCar(URL url);

}