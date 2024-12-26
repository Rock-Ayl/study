package com.rock.dubbo.dubbo_spi_adaptive_expansion;

import org.apache.dubbo.common.extension.ExtensionLoader;

import java.net.URL;
import java.net.URLDecoder;

/**
 * 自适应 制作车轮 实现类
 * 本质：一个代理类,通过 SPI 实现代理效果.
 *
 * @Author ayl
 * @Date 2024-12-26
 */
public class AdaptiveWheelMaker implements WheelMaker {

    @Override
    public Wheel makeWheel(URL url) {
        // 1.从 URL 中获取 WheelMaker 名称
        String wheelMakerName = getParameter(url, "Wheel.maker");
        // 2.通过 SPI 加载具体的 WheelMaker
        WheelMaker wheelMaker = ExtensionLoader.getExtensionLoader(WheelMaker.class).getExtension(wheelMakerName);
        // 3.调用目标方法
        return wheelMaker.makeWheel(url);
    }

    /**
     * 获取url中，指定参数
     *
     * @param url
     * @param target 目标参数
     * @return
     */
    private String getParameter(URL url, String target) {
        //获取
        String query = url.getQuery();
        //判空
        if (query == null) {
            //过
            return null;
        }
        try {
            //拆分
            String[] pairs = query.split("&");
            //循环
            for (String pair : pairs) {
                //获取 = 索引
                int idx = pair.indexOf("=");
                //确保有 '=' 符号
                if (idx < 1) {
                    //本轮过
                    continue;
                }
                //获取key
                String key = URLDecoder.decode(pair.substring(0, idx), "UTF-8");
                //判断
                if (target.equals(key)) {
                    //返回value
                    return URLDecoder.decode(pair.substring(idx + 1), "UTF-8");
                }
            }
        } catch (Exception e) {

        }
        //默认
        return null;
    }

}
