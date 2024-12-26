package com.rock.dubbo.adaptive.java.maker.wheel;

import com.rock.dubbo.adaptive.java.bo.Wheel;
import org.apache.dubbo.common.URL;

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
