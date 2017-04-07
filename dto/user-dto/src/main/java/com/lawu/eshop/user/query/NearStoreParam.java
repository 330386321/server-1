package com.lawu.eshop.user.query;

import com.lawu.eshop.framework.core.page.AbstractPageParam;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author meishuquan
 * @date 2017/4/7.
 */
public class NearStoreParam extends AbstractPageParam {

    @ApiModelProperty(value = "经度", required = true)
    private Double longitude;

    @ApiModelProperty(value = "纬度", required = true)
    private Double latitude;

    @ApiModelProperty(value = "主营业务")
    private String industryPath;

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

    public String getIndustryPath() {
        return industryPath;
    }

    public void setIndustryPath(String industryPath) {
        this.industryPath = industryPath;
    }
}
