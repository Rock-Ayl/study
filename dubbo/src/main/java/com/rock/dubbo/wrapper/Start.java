package com.rock.dubbo.wrapper;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.ExtensionLoader;

/**
 * 启动
 *
 * @Author ayl
 * @Date 2024-12-26
 */
public class Start {

    public static void main(String[] args) {

        //模拟url
        URL url = URL.valueOf("http://192.168.0.101:20880/");

        /**
         * 代理 spi 自适配扩展 + wrapper
         *
         * 结果：自适配扩展会先执行拦截器、按照文件内部依次执行、最后执行真实实现
         */

        //载入
        ExtensionLoader<WTest> wTestExtensionLoader = ExtensionLoader.getExtensionLoader(WTest.class);
        //执行 自适配扩展 wrapper
        wTestExtensionLoader.getAdaptiveExtension().test(url);

    }

}
