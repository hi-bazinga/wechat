package com.zxczone.littlefox.pojo.baidumap;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Data;

/**
 *
 * @author Jason Zhao
 */
@Data
public class CoordConvResult {

    @JsonProperty("x")
    private String longitude;
    
    @JsonProperty("y")
    private String latitude;
    
}