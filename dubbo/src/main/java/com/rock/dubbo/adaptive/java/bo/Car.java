package com.rock.dubbo.adaptive.java.bo;

/**
 * 汽车实体
 *
 * @Author ayl
 * @Date 2024-12-26
 */
public class Car {

    //车需要轮子对象
    private Wheel wheel;

    /**
     * 初始化汽车
     *
     * @param wheel
     */
    public Car(Wheel wheel) {
        this.wheel = wheel;
    }

}