package com.itmayiedu.api.service;

import com.itmayiedu.base.ResponseBase;
import com.itmayiedu.entity.UserEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @ClassName dell
 * @Description TOOD
 * @Author X
 * @Data 2019/12/10-16:00
 * @Version 1.0
 **/
@RequestMapping("/member")
public interface MemberService {
    /**
     * @title:
     * @description: 使用用户id查找用户信息
     * @author: X
     * @updateTime: 2019/12/10 16:01
     * @return:
     * @param:
     * @throws:
     */
    @RequestMapping("findUserById")
    ResponseBase findUserById(Long userId);

    /**
     * @title:
     * @description: 用户注册
     * @author: X
     * @updateTime: 2019/12/11 15:40
     * @return:
     * @param:
     * @throws:
     */
    @RequestMapping("/regUser")
    ResponseBase regUser(@RequestBody UserEntity user);

    /**
     * @title:
     * @description: 用户登录
     * @author: X
     * @updateTime: 2019/12/11 16:14
     * @return:
     * @param:
     * @throws:
     */
    @RequestMapping("/login")
    ResponseBase login(@RequestBody UserEntity user);

    /**
     * @title:
     * @description: 使用Token验证实现用户登录
     * @author: X
     * @updateTime: 2019/12/11 16:16
     * @return:
     * @param:
     * @throws:
     */
    @RequestMapping("/findByTokenUser")
    ResponseBase findByTokenUser(String token);
}
