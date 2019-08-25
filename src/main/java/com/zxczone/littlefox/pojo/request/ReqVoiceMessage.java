package com.zxczone.littlefox.pojo.request;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * Request Voice Message
 *
 * @author Jason Zhao
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class ReqVoiceMessage extends ReqMessage{
   
    private String MediaId;  
    
    private String Format; 
    
    private String Recognition;

}
