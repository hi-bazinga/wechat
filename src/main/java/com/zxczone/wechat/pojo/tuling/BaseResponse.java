package com.zxczone.wechat.pojo.tuling;

import lombok.Data;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * Base response
 *
 * @author Jason Zhao
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BaseResponse {
    
    private String code;
    
    private String text;
}
