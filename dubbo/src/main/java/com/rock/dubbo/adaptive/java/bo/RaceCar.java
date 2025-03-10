package com.rock.dubbo.adaptive.java.bo;

/**
 * 赛车实体 ，继承汽车
 *
 * @Author ayl
 * @Date 2024-12-26
 */
public class RaceCar extends Car {

    /**
     * 初始化汽车
     *
     * @param wheel
     */
    public RaceCar(Wheel wheel) {
        //传给父类
        super(wheel);
    }

}