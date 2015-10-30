package com.zxczone.wechat.pojo;

import java.util.List;

import lombok.Data;

/**
 * Location information object
 *
 * @author Jason Zhao
 */
@Data
public class LocationInfo {
    
    private Location location;
    
    private String sematic_description;
    
    private String formatted_address;
    
    private String business;
    
    private String cityCode;
    
    private List<PoiRegion> poiRegions;
    
    private AddressComponent addressComponent;

}
