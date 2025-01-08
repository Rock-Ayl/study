package com.rock.dubbo.adaptive.dubbo;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.ExtensionLoader;

import java.util.List;

/**
 * @Author ayl
 * @Date 2024-12-26
 */
public class Start {

    public static void main(String[] args) {

        /**
         * Dubbo SPI{@link ExtensionLoader} 本质是代替了 Java SPI 的 {@link java.util.ServiceLoader}
         */

        //载入
        ExtensionLoader<Animal> loader = ExtensionLoader.getExtensionLoader(Animal.class);

        /**
         * spi 实现
         */

        //非代理 spi 实现
        loader.getExtension("cat").eat(null);
        loader.getExtension("dog").eat(null);

        /**
         * spi + 自适配扩展{@link org.apache.dubbo.common.extension.Adaptive} 实现
         */

        System.out.println("############# spi + 自适配扩展 实现 ################");

        //没有值，默认dog
        loader.getAdaptiveExtension().eat(URL.valueOf("http://localhost:9999/xxx"));
        //匹配正确，加载cat
        loader.getAdaptiveExtension().eat(URL.valueOf("http://localhost:9999/xxx?animalType=cat"));
        //匹配另一个字段，加载cat
        loader.getAdaptiveExtension().eat(URL.valueOf("http://localhost:9999/xxx?animalType2=cat"));

        /**
         * spi + {@link org.apache.dubbo.common.extension.Activate} 实现
         */

        System.out.println("############# spi + active 实现 ################");

        //获取所有 符合条件 的可用spi
        List<Animal> activateExtensionList = loader.getActivateExtension(
                URL.valueOf("http://localhost:9999/xxx?animalType=dog"),
                //不考虑group,满足url的参数,即使不是Activate类,也可以获取到
                "animalType",
                //不考虑key,满足分组,即算符合条件
                "分组1"
        );
        //循环
        for (Animal animal : activateExtensionList) {
            //实现
            animal.eat(URL.valueOf("http://localhost:9999/xxx?animalType=dog"));
        }

    }

}
