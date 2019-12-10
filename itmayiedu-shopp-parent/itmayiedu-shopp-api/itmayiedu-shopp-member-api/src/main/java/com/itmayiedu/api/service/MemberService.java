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

    @RequestMapping("/regUser")
    ResponseBase regUser(@RequestBody UserEntity user);
}
