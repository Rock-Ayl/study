package com.rock.ai.deepseek;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * DeepSeek 请求参数实体
 *
 * @Author ayl
 * @Date 2025-02-07
 */
@Getter
@Setter
public class DeepSeekRequestBody {

    //todo 还有很多选填参数

    //模型[普通模型=deepseek-chat][推理模型=deepseek-reasoner]
    private String model;

    //是否为流式返回
    private Boolean stream;

    //消息列表
    private List<Message> messages;

    /**
     * 单个消息对象
     */
    @Getter
    @Setter
    public static class Message {

        //角色
        private String role;

        //内容
        private String content;

    }

}
