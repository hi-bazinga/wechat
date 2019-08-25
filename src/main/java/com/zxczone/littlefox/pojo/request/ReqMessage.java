package com.zxczone.littlefox.pojo.request;

import lombok.Data;

/**
 * Base Class of Request Message
 *
 * @author Jason Zhao
 */
@Data
public class ReqMessage {

    private String ToUserName;
    
    private String FromUserName;
    
    private long CreateTime;
    
    private String MsgType;
    
    private long MsgId;

}
