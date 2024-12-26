package com.rock.dubbo.adaptive.dubbo;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.Adaptive;
import org.apache.dubbo.common.extension.SPI;

/**
 * 动物接口
 *
 * @Author ayl
 * @Date 2024-12-26
 */
//默认的值狗
@SPI("dog")
public interface Animal {

    /**
     * 接口的方法需要添加这个注解，在测试代码中，参数至少要有一个URL类型的参数
     * 动物类型方式
     *
     * @param url
     */
    @Adaptive({"animalType", "animalType2"})
    void eat(URL url);

}