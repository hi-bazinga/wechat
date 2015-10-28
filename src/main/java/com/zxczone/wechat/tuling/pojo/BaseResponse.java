package com.zxczone.wechat.tuling.pojo;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Base response
 *
 * @author Jason Zhao
 */
@JsonIgnoreProperties(ignoreUnknown = true)
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
