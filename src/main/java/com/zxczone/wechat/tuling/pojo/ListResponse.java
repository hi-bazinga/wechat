package com.zxczone.wechat.tuling.pojo;

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
