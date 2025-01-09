package com.rock.dubbo.wrapper;

import org.apache.dubbo.common.URL;

/**
 * 真实的执行逻辑(最里层)
 *
 * @Author ayl
 * @Date 2025-01-09
 */
public class RealWTest implements WTest {

    @Override
    public void test(URL url) {
        System.out.println(url);
    }

}