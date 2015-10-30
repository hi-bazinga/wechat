package com.zxczone.wechat.pojo.tuling;

import java.util.List;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * List response
 *
 * @author Jason Zhao
 */
@Data
@EqualsAndHashCode(callSuper=false)
public class ListResponse<T> extends BaseResponse{

    private List<T> list;
    
}
