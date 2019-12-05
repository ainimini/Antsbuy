package com.itmayiedu.base;

import com.itmayiedu.constants.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @ClassName dell
 * @Description TOOD
 * @Author X
 * @Data 2019/12/5
 * @Version 1.0
 **/
@Component
public class BaseApiService {

    @Autowired
    protected BaseRedisService baseRedisService;

    //返回成功 不可传data值
    public ResponseBase setResultSuccess() {

        return setResult(Constants.HTTP_RES_CODE_200, Constants.HTTP_RES_CODE_200_VALUE, null);
    }

    //返回成功 可传data值
    public ResponseBase setResultSuccess(Object data) {

        return setResult(Constants.HTTP_RES_CODE_200, Constants.HTTP_RES_CODE_200_VALUE, data);
    }

    //返回失败 可传msg
    public ResponseBase setResultError(String rtnMsg) {

        return setResult(Constants.HTTP_RES_CODE_500, rtnMsg, null);
    }

    /**
     * @title:
     * @description: 通用封装
     * @author: X
     * @updateTime: 2019/12/5 21:07
     * @return:
     * @param:
     * @throws:
     */

    public ResponseBase setResult(Integer rtnCode, String rtnMsg, Object data) {

        return new ResponseBase(rtnCode, rtnMsg, data);
    }
}
