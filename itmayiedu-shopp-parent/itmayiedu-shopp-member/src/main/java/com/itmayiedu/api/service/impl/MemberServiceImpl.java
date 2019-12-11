package com.itmayiedu.api.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.itmayiedu.api.service.MemberService;
import com.itmayiedu.base.BaseApiService;
import com.itmayiedu.base.BaseRedisService;
import com.itmayiedu.base.ResponseBase;
import com.itmayiedu.constants.Constants;
import com.itmayiedu.dao.MemberDao;
import com.itmayiedu.entity.UserEntity;
import com.itmayiedu.mq.RegisterMailboxProducer;
import com.itmayiedu.utils.MD5Util;
import com.itmayiedu.utils.TokenUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.activemq.command.ActiveMQQueue;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


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

    /**
     * @title:
     * @description: 通过id查询用户
     * @author: X
     * @updateTime: 2019/12/11 15:42
     * @return:
     * @param:
     * @throws:
     */
    @Override
    public ResponseBase findUserById(Long userId) {
        UserEntity user = memberDao.findByID(userId);
        if (user == null) {
            return setResultError("未找到用户信息");
        }
        return setResultSuccess(user);
    }

    /**
     * @title:
     * @description: 用户注册
     * @author: X
     * @updateTime: 2019/12/11 15:41
     * @return:
     * @param:
     * @throws:
     */
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

    /**
     * @title:
     * @description: 用户登录
     * @author: X
     * @updateTime: 2019/12/11 15:43
     * @return:
     * @param:
     * @throws:
     */
    @Override
    public ResponseBase login(@RequestBody UserEntity user) {
        //参数验证
        String username = user.getUsername();
        String password = user.getPassword();
        if (StringUtils.isEmpty(username)) {
            return setResultError("用户名不能为空");
        }
        if (StringUtils.isEmpty(password)) {
            return setResultError("密码不能为空");
        }
        //数据库查找账号和密码是否正确
        String passwordMD5 = MD5Util.MD5(password);
        UserEntity userEntity = memberDao.login(username, passwordMD5);
        if (userEntity == null) {
            return setResultError("用户名和密码不正确");
        }
        //如果找号和密码正确 生成对应的token
        String memberToken = TokenUtils.getMemberToken();
        //存放在redis中 key为token value为userId
        Integer userId = userEntity.getId();
        log.info("###########用户信息存放在Redis中。。。key:{},value###########", memberToken, userId);
        baseRedisService.setString(memberToken, userId + "", Constants.TOKEN_MEMBER_TIME);
        //直接返回token
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("memberToken", memberToken);
        return setResultSuccess(jsonObject);
    }

    /**
     * @title:
     * @description: 使用Token验证实现用户登录
     * @author: X
     * @updateTime: 2019/12/11 16:17
     * @return:
     * @param:
     * @throws:
     */
    @Override
    public ResponseBase findByTokenUser(String token) {
        //验证参数
        if (StringUtils.isEmpty(token)) {
            return setResultError("token不能为空");
        }
        //从Redis中使用Token查找对应的userId
        String strUserId = baseRedisService.getString(token);
        if (StringUtils.isEmpty(strUserId)) {
            return setResultError("token无效或者已过期");
        }
        //使用userId去数据库中查询用户信息返回给客户端
        long userId = Long.parseLong(strUserId);
        UserEntity userEntity = memberDao.findByID(userId);
        if (userEntity == null) {
            return setResultError("为查找到该用户信息");
        }
        userEntity.setPassword(null);
        return setResultSuccess(userEntity);
    }

    /**
     * @title:
     * @description: 发送email
     * @author: X
     * @updateTime: 2019/12/11 15:42
     * @return:
     * @param:
     * @throws:
     */
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
