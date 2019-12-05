package com.itmayiedu.api.service;

import com.itmayiedu.base.ResponseBase;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

/**
 * @ClassName dell
 * @Description TOOD
 * @Author X
 * @Data 2019/12/5-20:24
 * @Version 1.0
 **/
@RequestMapping("/member")
public interface TestApiService {
    @RequestMapping("/test")
    public Map<String, Object> test(Integer id, String name);

    //测试返回样式
    @RequestMapping("/testResponseBase")
    public ResponseBase testResponseBase();

    //测试redis
    @RequestMapping("/testRedis")
    public ResponseBase setTestRedis(String key,String value);
}
