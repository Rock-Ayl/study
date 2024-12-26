package com.rock.dubbo.adaptive.java.maker.wheel;

import com.rock.dubbo.adaptive.java.bo.Wheel;
import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.SPI;

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