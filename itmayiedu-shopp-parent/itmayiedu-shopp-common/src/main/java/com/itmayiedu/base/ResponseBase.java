package com.itmayiedu.base;

import lombok.Setter;

/**
 * @ClassName dell
 * @Description TOOD
 * @Author X
 * @Data 2019/12/5
 * @Version 1.0
 **/
public class ResponseBase {

    private Integer rtnCode;
    private String rtnMsg;
    private Object data;

    public ResponseBase(Integer rtnCode, String rtnMsg, Object data) {
        this.rtnCode = rtnCode;
        this.rtnMsg = rtnMsg;
        this.data = data;
    }

    public ResponseBase(){

    }

    public Integer getRtnCode() {
        return rtnCode;
    }

    public void setRtnCode(Integer rtnCode) {
        this.rtnCode = rtnCode;
    }

    public String getRtnMsg() {
        return rtnMsg;
    }

    public void setRtnMsg(String rtnMsg) {
        this.rtnMsg = rtnMsg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
