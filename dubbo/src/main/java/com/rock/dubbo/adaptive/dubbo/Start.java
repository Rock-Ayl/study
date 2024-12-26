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
        //获取自适配扩展
        Animal animal = loader.getAdaptiveExtension();

        //没有值，默认dog
        animal.eat(URL.valueOf("http://localhost:9999/xxx"));
        //匹配正确，加载cat
        animal.eat(URL.valueOf("http://localhost:9999/xxx?animalType=cat"));

    }

}
