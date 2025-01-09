package com.rock.dubbo.wrapper;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.Adaptive;
import org.apache.dubbo.common.extension.SPI;

/**
 * SPI 定义
 */
@SPI("wTest")
public interface WTest {

    /**
     * 自适配扩展方法
     *
     * @param url
     */
    @Adaptive
    void test(URL url);

}