package com.rock.dubbo.adaptive.java.maker.car;

import com.rock.dubbo.adaptive.java.bo.Car;
import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.SPI;

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