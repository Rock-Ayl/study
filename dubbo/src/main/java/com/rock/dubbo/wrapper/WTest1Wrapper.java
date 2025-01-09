package com.rock.dubbo.wrapper;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.ExtensionLoader;

/**
 * 拦截器 1
 *
 * @Author ayl
 * @Date 2025-01-09
 */
public class WTest1Wrapper implements WTest {

    //包装传递链
    private final WTest wTest;

    /**
     * {@link ExtensionLoader} .isWrapperClass 会通过判断是否有该构造函数,判断其是否为包装类,并进行传递
     *
     * @param wTest
     */
    public WTest1Wrapper(WTest wTest) {
        this.wTest = wTest;
    }

    @Override
    public void test(URL url) {
        System.out.println("拦截器-1-前");
        wTest.test(url);
        System.out.println("拦截器-1-后");
    }

}