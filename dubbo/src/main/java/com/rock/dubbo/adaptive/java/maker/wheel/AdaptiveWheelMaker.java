package com.rock.dubbo.adaptive.java.maker.wheel;

import com.rock.dubbo.adaptive.java.bo.Wheel;
import org.apache.dubbo.common.URL;
import org.apache.dubbo.common.extension.ExtensionLoader;

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
        String wheelMakerName = url.getParameter("wheel.maker");
        // 2.通过 SPI 加载具体的 WheelMaker
        WheelMaker wheelMaker = ExtensionLoader.getExtensionLoader(WheelMaker.class).getExtension(wheelMakerName);
        // 3.调用目标方法
        return wheelMaker.makeWheel(url);
    }

}
