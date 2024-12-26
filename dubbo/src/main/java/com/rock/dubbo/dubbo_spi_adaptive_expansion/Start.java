package com.rock.dubbo.dubbo_spi_adaptive_expansion;

import com.rock.dubbo.dubbo_spi_adaptive_expansion.maker.car.CarMaker;
import com.rock.dubbo.dubbo_spi_adaptive_expansion.maker.wheel.WheelMaker;
import org.apache.dubbo.common.extension.ExtensionLoader;

/**
 * 启动
 *
 * @Author ayl
 * @Date 2024-12-26
 */
public class Start {

    public static void main(String[] args) {

        //载入
        ExtensionLoader<WheelMaker> wheelMakerExtensionLoader = ExtensionLoader.getExtensionLoader(WheelMaker.class);
        ExtensionLoader<CarMaker> carMakerExtensionLoader = ExtensionLoader.getExtensionLoader(CarMaker.class);

    }

}
