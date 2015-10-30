package com.zxczone.wechat.pojo;

import lombok.Data;

/**
 *
 * @author Jason Zhao
 */
@Data
public class BaiduResponse<T> {

    private String status;
    
    private T result;
    
}