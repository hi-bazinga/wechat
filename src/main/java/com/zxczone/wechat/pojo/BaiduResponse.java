package com.zxczone.wechat.pojo;

/**
 * TODO
 *
 * @author Jason Zhao
 */
public class BaiduResponse {

    private String status;
    
    private LocationInfo result;
    
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public LocationInfo getResult() {
        return result;
    }
    public void setResult(LocationInfo result) {
        this.result = result;
    }
    
}
