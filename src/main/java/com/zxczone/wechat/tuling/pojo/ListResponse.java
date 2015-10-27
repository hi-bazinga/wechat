package com.zxczone.wechat.tuling.pojo;

import java.util.List;

/**
 * List response
 *
 * @author Jason Zhao
 */
public class ListResponse<T> extends BaseResponse{

    private List<T> list;

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }
    
}
