package com.rock.dubbo.adaptive.java;

import com.rock.dubbo.adaptive.java.bo.Wheel;
import com.rock.dubbo.adaptive.java.maker.wheel.WheelMaker;
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
        //获取代理
        WheelMaker adaptive = wheelMakerExtensionLoader.getExtension("AdaptiveWheelMaker");
        //通过代理创建轮胎
        Wheel wheel = adaptive.makeWheel(url);
        //输出
        System.out.println(wheel);

        //todo 制造赛车,暂时没有注入 setWheelMaker
        //ExtensionLoader<CarMaker> carMakerExtensionLoader = ExtensionLoader.getExtensionLoader(CarMaker.class);
        //获取赛车实例
        //CarMaker carMaker = carMakerExtensionLoader.getExtension("RaceCarMaker");
        //carMaker.makeCar(url);

    }

}
