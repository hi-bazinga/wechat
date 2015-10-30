package com.zxczone.wechat.pojo.response;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Response Text Message
 *
 * @author Jason Zhao
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class ResTextMessage extends ResMessage{

    private String Content;
    
}
