package com.rock.dubbo.adaptive.dubbo;

import org.apache.dubbo.common.URL;

/**
 * 实现 狗
 *
 * @Author ayl
 * @Date 2024-12-26
 */
public class Dog implements Animal {

    @Override
    public void eat(URL url) {
        System.out.println("i am a dog");
    }

}