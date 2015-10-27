package com.zxczone.wechat.tuling.pojo;

/**
 * Base response
 *
 * @author Jason Zhao
 */
public class BaseResponse {

    private String code;
    
    private String text;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
    
}
