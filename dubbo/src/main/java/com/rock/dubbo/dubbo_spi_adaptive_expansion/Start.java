package com.rock.dubbo.dubbo_spi_adaptive_expansion;

import com.rock.dubbo.dubbo_spi_adaptive_expansion.maker.car.CarMaker;
import com.rock.dubbo.dubbo_spi_adaptive_expansion.maker.wheel.WheelMaker;
import org.apache.dubbo.common.extension.ExtensionLoader;

import java.net.MalformedURLException;
import java.net.URL;

/**
 * 启动
 *
 * @Author ayl
 * @Date 2024-12-26
 */
public class Start {

    public static void main(String[] args) throws MalformedURLException {

        //模拟url
        URL url = new URL("http://192.168.0.101:20880/XxxService?wheel.maker=MichelinWheelMaker");

        //载入
        ExtensionLoader<WheelMaker> wheelMakerExtensionLoader = ExtensionLoader.getExtensionLoader(WheelMaker.class);
        ExtensionLoader<CarMaker> carMakerExtensionLoader = ExtensionLoader.getExtensionLoader(CarMaker.class);

        //获取实例代理实例
        WheelMaker adaptive = wheelMakerExtensionLoader.getExtension("AdaptiveWheelMaker");
        //通过代理创建轮胎
        System.out.println(adaptive.makeWheel(url));

    }

}
