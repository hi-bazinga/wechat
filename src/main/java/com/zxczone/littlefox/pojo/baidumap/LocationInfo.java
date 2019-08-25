package com.zxczone.littlefox.pojo.baidumap;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * Location information object
 *
 * @author Jason Zhao
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LocationInfo {
    
    private Location location;
    
    private String sematic_description;
    
    private String formatted_address;
    
    private String business;
    
    private String cityCode;
    
    private List<PoiRegion> poiRegions;
    
    private AddressComponent addressComponent;

}
