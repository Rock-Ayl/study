package com.rock.dubbo.adaptive.java.bo;

/**
 * 车轮实体
 *
 * @Author ayl
 * @Date 2024-12-26
 */
public class Wheel {

    //名称
    private String name;

    //初始化
    public Wheel(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format("name=%s", this.name);
    }

}