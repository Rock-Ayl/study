package com.rock.dubbo.wrapper;

import org.apache.dubbo.common.URL;

/**
 * 拦截器 2
 *
 * @Author ayl
 * @Date 2025-01-09
 */
public class WTest2Wrapper implements WTest {

    private final WTest wTest;

    public WTest2Wrapper(WTest wTest) {
        this.wTest = wTest;
    }

    @Override
    public void test(URL url) {
        System.out.println("拦截器-2-前");
        wTest.test(url);
        System.out.println("拦截器-2-后");
    }

}