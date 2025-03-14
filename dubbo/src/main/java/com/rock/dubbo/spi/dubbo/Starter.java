package com.rock.dubbo.spi.dubbo;

import org.apache.dubbo.common.extension.ExtensionLoader;

/**
 * Java SPI 的执行
 * 想要实现效果，必须在 /resources/META-INF/dubbo/ 下创建 {@link Service} 文件，并写入方法配置
 *
 * @Author ayl
 * @Date 2024-12-25
 */
public class Starter {

    public static void main(String[] args) {

        //载入
        ExtensionLoader<Service> extensionLoader = ExtensionLoader.getExtensionLoader(Service.class);

        //实现
        extensionLoader.getExtension("dubboSpiKey1").hello();
        extensionLoader.getExtension("dubboSpiKey2").hello();
        //再度实现，发现时间是一样，说明用的是缓存的实例
        extensionLoader.getExtension("dubboSpiKey1").hello();
        extensionLoader.getExtension("dubboSpiKey1").hello();
        extensionLoader.getExtension("dubboSpiKey1").hello();
        extensionLoader.getExtension("dubboSpiKey2").hello();
        extensionLoader.getExtension("dubboSpiKey2").hello();

    }

}
