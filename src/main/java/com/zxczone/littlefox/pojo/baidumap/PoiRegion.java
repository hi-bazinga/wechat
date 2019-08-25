package com.zxczone.littlefox.pojo.baidumap;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 *
 * @author Jason Zhao
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class PoiRegion {

    private String direction_desc;
    
    private String name;
    
}
