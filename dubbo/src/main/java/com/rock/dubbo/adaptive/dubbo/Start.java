package com.rock.dubbo.adaptive.dubbo;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.ExtensionLoader;

/**
 * @Author ayl
 * @Date 2024-12-26
 */
public class Start {

    public static void main(String[] args) {
        //载入
        ExtensionLoader<Animal> loader = ExtensionLoader.getExtensionLoader(Animal.class);

        /**
         * spi 实现
         */

        //非代理 spi 实现
        loader.getExtension("cat").eat(null);
        loader.getExtension("dog").eat(null);

        /**
         * spi + 自适配扩展 实现
         */

        //没有值，默认dog
        loader.getAdaptiveExtension().eat(URL.valueOf("http://localhost:9999/xxx"));
        //匹配正确，加载cat
        loader.getAdaptiveExtension().eat(URL.valueOf("http://localhost:9999/xxx?animalType=cat"));
        //匹配另一个字段，加载cat
        loader.getAdaptiveExtension().eat(URL.valueOf("http://localhost:9999/xxx?animalType2=cat"));

    }

}
