package com.zxczone.wechat.pojo;

import java.util.List;

/**
 * Location information object
 *
 * @author Jason Zhao
 */
public class LocationInfo {
    
    private Location location;
    
    private String sematic_description;
    
    private String formatted_address;
    
    private String business;
    
    private String cityCode;
    
    private List<PoiRegion> poiRegions;
    
    private AddressComponent addressComponent;

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getSematic_description() {
        return sematic_description;
    }

    public void setSematic_description(String sematic_description) {
        this.sematic_description = sematic_description;
    }

    public String getFormatted_address() {
        return formatted_address;
    }

    public void setFormatted_address(String formatted_address) {
        this.formatted_address = formatted_address;
    }

    public String getBusiness() {
        return business;
    }

    public void setBusiness(String business) {
        this.business = business;
    }

    public String getCityCode() {
        return cityCode;
    }

    public void setCityCode(String cityCode) {
        this.cityCode = cityCode;
    }

    public List<PoiRegion> getPoiRegions() {
        return poiRegions;
    }

    public void setPoiRegions(List<PoiRegion> poiRegions) {
        this.poiRegions = poiRegions;
    }

    public AddressComponent getAddressComponent() {
        return addressComponent;
    }

    public void setAddressComponent(AddressComponent addressComponent) {
        this.addressComponent = addressComponent;
    }

}
