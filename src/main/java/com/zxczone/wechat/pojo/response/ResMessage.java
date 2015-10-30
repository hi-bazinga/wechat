package com.zxczone.wechat.pojo.response;

import lombok.Data;

/**
 * Base Class of Response Message
 *
 * @author Jason Zhao
 */
@Data
public class ResMessage {

    private String ToUserName;
    
    private String FromUserName;
    
    private long CreateTime;
    
    private String MsgType;
    
}
