package com.zxczone.littlefox.pojo.baidumap;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 *
 * @author Jason Zhao
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class AddressComponent {

    private String city;
    
    private String country;
    
    private String direction;
    
    private String distance;
    
    private String district;
    
    private String province;
    
    private String street;
    
    private String street_number;
    
    private String country_code;

    private String country_code_iso;

    private String country_code_iso2;

}
