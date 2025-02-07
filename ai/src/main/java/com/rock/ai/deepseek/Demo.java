package com.rock.ai.deepseek;

import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import com.rock.ai.utils.FastJsonExtraUtils;

import java.util.Arrays;

/**
 * 调用 DeepSeek
 *
 * @Author ayl
 * @Date 2025-02-06
 */
public class Demo {

    //请求地址
    private static final String DEEP_SEEK_POST_URL = "https://api.deepseek.com/chat/completions";

    //我们的 api key
    private static final String API_KEY = "";

    public static void main(String[] args) {

        /**
         * 普通 V3 模型 构建参数
         */

        //初始化参数
        DeepSeekRequestBody requestBody = new DeepSeekRequestBody();
        //指定模型
        requestBody.setModel("deepseek-chat");
        //暂定不返回流
        requestBody.setStream(false);

        //告诉ai它的角色
        DeepSeekRequestBody.Message message1 = new DeepSeekRequestBody.Message();
        message1.setRole("system");
        message1.setContent("你是一个Java开发工程师.");

        //跟ai说话
        DeepSeekRequestBody.Message message2 = new DeepSeekRequestBody.Message();
        message2.setRole("user");
        message2.setContent("你好!");

        //组装消息体
        requestBody.setMessages(Arrays.asList(message1, message2));

        /**
         * 普通 V3 模型 请求
         */

        //组装请求
        HttpResponse response = HttpRequest.post(DEEP_SEEK_POST_URL)
                //参数类型
                .header("Content-Type", "application/json")
                //api key
                .header("Authorization", "Bearer " + API_KEY)
                //参数体
                .body(FastJsonExtraUtils.toJSONString(requestBody))
                //请求实现
                .execute();
        //获取返回内容
        String body = response.body();
        //输出结果
        System.out.println(body);

    }

}