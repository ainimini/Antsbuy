package com.itmayiedu.email.service;

import com.alibaba.fastjson.JSONObject;
import com.itmayiedu.adapter.MessageAdapter;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

/**
 * @ClassName dell
 * @Description 处理第三方发送邮件
 * @Author X
 * @Data 2019/12/10
 * @Version 1.0
 **/
@Service
public class EmailService implements MessageAdapter {
    @Override
    public void sendMsg(JSONObject body) {

        //处理发送邮件
        String email = body.getString("email");
        if (StringUtils.isEmpty(email)){
            return;
        }

    }
}
