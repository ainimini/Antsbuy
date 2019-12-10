package com.itmayiedu.api.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.itmayiedu.api.service.MemberService;
import com.itmayiedu.base.BaseApiService;
import com.itmayiedu.base.ResponseBase;
import com.itmayiedu.constants.Constants;
import com.itmayiedu.dao.MemberDao;
import com.itmayiedu.entity.UserEntity;
import com.itmayiedu.mq.RegisterMailboxProducer;
import com.itmayiedu.utils.MD5Util;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.commons.lang3.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.mail.Header;

/**
 * @ClassName dell
 * @Description TOOD
 * @Author X
 * @Data 2019/12/10
 * @Version 1.0
 **/
@Slf4j
@RestController
public class MemberServiceImpl extends BaseApiService implements MemberService {

    @Autowired
    private MemberDao memberDao;
    @Autowired
    private RegisterMailboxProducer registerMailboxProducer;
    @Value("${messages.queue}")
    private String MESSAGEQUEUE;

    @Override
    public ResponseBase findUserById(Long userId) {
        UserEntity user = memberDao.findByID(userId);
        if (user == null) {
            return setResultError("未找到用户信息");
        }
        return setResultSuccess(user);
    }

    @Override
    public ResponseBase regUser(@RequestBody UserEntity user) {
        //参数验证
        String password = user.getPassword();
        if (StringUtils.isEmpty(password)) {
            return setResultError("密码不能为空！");
        }
        //棉麻MD5加密
        String passwordMD5 = MD5Util.MD5(password);
        user.setPassword(passwordMD5);
        Integer result = memberDao.insertUser(user);
        if (result <= 0) {
            return setResultError("注册信息失败！");
        }
        //采用异步发送消息
        String email = user.getEmail();
        String json = emailJson(email);
        log.info("############会员服务推送消息到消息服务平台############json:{}", json);
        sendMsg(json);
        return setResultSuccess("注册信息成功！");
    }

    private String emailJson(String email) {
        JSONObject jsonObject = new JSONObject();
        JSONObject header = new JSONObject();
        header.put("interfaceType", Constants.SMS_MAIL);
        JSONObject content = new JSONObject();
        content.put("email", email);
        jsonObject.put("header", header);
        jsonObject.put("content", content);
        return jsonObject.toJSONString();
    }

    private void sendMsg(String json) {
        ActiveMQQueue activeMQQueue = new ActiveMQQueue(MESSAGEQUEUE);
        registerMailboxProducer.sendMsg(activeMQQueue, json);
    }

}
