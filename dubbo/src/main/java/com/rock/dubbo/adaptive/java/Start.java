package com.rock.dubbo.adaptive.java;

import com.rock.dubbo.adaptive.java.bo.Wheel;
import com.rock.dubbo.adaptive.java.maker.car.CarMaker;
import com.rock.dubbo.adaptive.java.maker.wheel.WheelMaker;
import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.ExtensionLoader;

/**
 * 启动
 *
 * @Author ayl
 * @Date 2024-12-26
 */
public class Start {

    public static void main(String[] args) {

        //模拟url
        URL url = URL.valueOf("http://192.168.0.101:20880/XxxService?wheel.maker=MichelinWheelMaker");

        /**
         * 代理 spi
         */

        //载入
        ExtensionLoader<WheelMaker> wheelMakerExtensionLoader = ExtensionLoader.getExtensionLoader(WheelMaker.class);
        //获取代理
        WheelMaker adaptive = wheelMakerExtensionLoader.getExtension("AdaptiveWheelMaker");
        //通过代理创建轮胎
        Wheel wheel = adaptive.makeWheel(url);
        //输出
        System.out.println(wheel);

        /**
         * 代理 spu + 注入
         * //todo 制造赛车,暂时没有注入 setWheelMaker
         */

        //载入赛车
        ExtensionLoader<CarMaker> carMakerExtensionLoader = ExtensionLoader.getExtensionLoader(CarMaker.class);

        //todo 没有工厂，获取赛车实例 CarMaker carMaker = carMakerExtensionLoader.getExtension("RaceCarMaker");
        //实现 carMaker.makeCar(url);

    }

}
