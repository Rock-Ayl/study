package com.rock.dubbo.dubbo_spi_adaptive_expansion.maker.wheel;

import com.rock.dubbo.dubbo_spi_adaptive_expansion.bo.Wheel;

import java.net.URL;

/**
 * @Author ayl
 * @Date 2024-12-26
 */
public class MichelinWheelMaker implements WheelMaker {

    @Override
    public Wheel makeWheel(URL url) {
        return new Wheel("MichelinWheelMaker");
    }

}
