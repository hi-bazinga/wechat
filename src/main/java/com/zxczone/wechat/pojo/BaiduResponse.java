package com.zxczone.wechat.pojo;

/**
 *
 * @author Jason Zhao
 */
public class BaiduResponse<T> {

    private String status;
    
    private T result;
    
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public T getResult() {
        return result;
    }
    public void setResult(T result) {
        this.result = result;
    }
    
}