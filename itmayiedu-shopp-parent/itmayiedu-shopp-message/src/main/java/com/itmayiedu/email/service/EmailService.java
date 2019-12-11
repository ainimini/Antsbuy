package com.itmayiedu.email.service;

import com.alibaba.fastjson.JSONObject;
import com.itmayiedu.adapter.MessageAdapter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * @ClassName dell
 * @Description 处理第三方发送邮件
 * @Author X
 * @Data 2019/12/10
 * @Version 1.0
 **/
@Service
@Slf4j
public class EmailService implements MessageAdapter {

    @Value("${msg.subject}")
    private String subject;
    @Value("${msg.text}")
    private String text;
    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void sendMsg(JSONObject body) {

        //处理发送邮件
        String email = body.getString("email");
        if (StringUtils.isEmpty(email)) {
            return;
        }
        log.info("消息服务平台发送邮箱：{}开始",email);
        SimpleMailMessage simpleMailMessage = new SimpleMailMessage();
        //来源账号
        simpleMailMessage.setFrom(email);
        //发送账号
        simpleMailMessage.setTo(email);
        //发送题目
        simpleMailMessage.setSubject(subject);
        //发送内容
        simpleMailMessage.setText(text.replace("{}",email));
        //发送邮件
        javaMailSender.send(simpleMailMessage);
        log.info("消息服务平台发送邮箱：{}完成",email);
    }
}
