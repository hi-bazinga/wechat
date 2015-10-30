package com.zxczone.wechat.tuling.pojo;

import lombok.Data;

/**
 * Train information
 *
 * @author Jason Zhao
 */
@Data
public class TrainInfo {
    
    private String trainnum;

    private String start;

    private String terminal;

    private String starttime;
        
    private String endtime;

    private String detailurl;

    private String icon;
    
}
