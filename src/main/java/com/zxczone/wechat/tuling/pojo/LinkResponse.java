package com.zxczone.wechat.tuling.pojo;

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
