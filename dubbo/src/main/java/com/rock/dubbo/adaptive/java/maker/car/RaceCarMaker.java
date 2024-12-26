package com.rock.dubbo.adaptive.java.maker.car;

import com.rock.dubbo.adaptive.java.bo.Car;
import com.rock.dubbo.adaptive.java.bo.RaceCar;
import com.rock.dubbo.adaptive.java.bo.Wheel;
import com.rock.dubbo.adaptive.java.maker.wheel.WheelMaker;

import java.net.URL;

/**
 * 制造赛车 实现
 *
 * @Author ayl
 * @Date 2024-12-26
 */
public class RaceCarMaker implements CarMaker {

    //赛车对应的 轮子制造者
    private WheelMaker wheelMaker;

    //通过 setter 注入 AdaptiveWheelMaker
    public void setWheelMaker(WheelMaker wheelMaker) {
        this.wheelMaker = wheelMaker;
    }

    public Car makeCar(URL url) {
        //初始化一个轮子
        Wheel wheel = this.wheelMaker.makeWheel(url);
        //初始化赛车
        return new RaceCar(wheel);
    }

}
