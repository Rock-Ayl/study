package com.rock.dubbo.java_spi;

import java.util.ServiceLoader;

/**
 * Java SPI 的执行
 * 想要实现效果，必须在 /resources/META-INF/services/ 下创建 {@link Service} 文件，并写入方法配置
 *
 * @Author ayl
 * @Date 2024-12-25
 */
public class Starter {

    public static void main(String[] args) {
        //载入
        ServiceLoader<Service> serviceLoader = ServiceLoader.load(Service.class);
        //这里会做初始化，并执行方法
        serviceLoader.forEach(Service::hello);
    }

}
