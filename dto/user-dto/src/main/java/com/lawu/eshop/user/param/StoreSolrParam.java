package com.lawu.eshop.user.param;

import com.lawu.eshop.framework.core.page.AbstractPageParam;
import com.lawu.eshop.user.constants.StoreSolrEnum;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author meishuquan
 * @date 2017/4/13.
 */
public class StoreSolrParam extends AbstractPageParam {

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "行业，全部为空字符串")
    private String industryPath;

    @ApiModelProperty(value = "距离(1,3,5...附近和全部为0)")
    private Integer distance;

    @ApiModelProperty(value = "区域，全部为空字符串")
    private String regionPath;

    @ApiModelProperty(value = "经度")
    private Double longitude;

    @ApiModelProperty(value = "纬度")
    private Double latitude;

    @ApiModelProperty(value = "排序类型")
    private StoreSolrEnum storeSolrEnum;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIndustryPath() {
        return industryPath;
    }

    public void setIndustryPath(String industryPath) {
        this.industryPath = industryPath;
    }

    public Integer getDistance() {
        return distance;
    }

    public void setDistance(Integer distance) {
        this.distance = distance;
    }

    public String getRegionPath() {
        return regionPath;
    }

    public void setRegionPath(String regionPath) {
        this.regionPath = regionPath;
    }

    public StoreSolrEnum getStoreSolrEnum() {
        return storeSolrEnum;
    }

    public void setStoreSolrEnum(StoreSolrEnum storeSolrEnum) {
        this.storeSolrEnum = storeSolrEnum;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }
}
