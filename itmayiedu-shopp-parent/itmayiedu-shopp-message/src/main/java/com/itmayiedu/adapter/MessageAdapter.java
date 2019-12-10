package com.itmayiedu.adapter;

import com.alibaba.fastjson.JSONObject;

/**
 * @ClassName dell
 * @Description 统一发送消息接口
 * @Author X
 * @Data 2019/12/10
 * @Version 1.0
 **/
public interface MessageAdapter {

    public void sendMsg(JSONObject body);
}
