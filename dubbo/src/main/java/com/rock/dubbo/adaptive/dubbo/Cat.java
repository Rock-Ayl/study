package com.rock.dubbo.adaptive.dubbo;

import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.Activate;

/**
 * 实现 猫
 *
 * @Author ayl
 * @Date 2024-12-26
 */
//指定其为 Activate 类、并指定分组
@Activate(group = "分组1")
public class Cat implements Animal {

    @Override
    public void eat(URL url) {
        System.out.println("hello cat~");
    }

}