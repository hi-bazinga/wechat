package com.zxczone.wechat.pojo.tuling;

import lombok.Data;
import lombok.EqualsAndHashCode;


/**
 * Link response
 *
 * @author Jason Zhao
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class LinkResponse extends BaseResponse{

    private String url;
    
}
