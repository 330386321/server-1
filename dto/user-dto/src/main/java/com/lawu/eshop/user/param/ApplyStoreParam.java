package com.lawu.eshop.user.param;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author zhangyong
 * @date 2017/4/21.
 */
public class ApplyStoreParam {
    /**
     * logo图片路径
     */
    @ApiModelProperty(name = "logoUrl", value = "logo图片路径")
    private String logoUrl;

    @ApiModelProperty(name = "environmentUrl", value = "门店环境图片路径")
    private String environmentUrl;

    @ApiModelProperty(name = "storeUrl", value = "门店图片路径")
    private String storeUrl;

    public String getLogoUrl() {
        return logoUrl;
    }

    public void setLogoUrl(String logoUrl) {
        this.logoUrl = logoUrl;
    }

    public String getEnvironmentUrl() {
        return environmentUrl;
    }

    public void setEnvironmentUrl(String environmentUrl) {
        this.environmentUrl = environmentUrl;
    }

    public String getStoreUrl() {
        return storeUrl;
    }

    public void setStoreUrl(String storeUrl) {
        this.storeUrl = storeUrl;
    }
}
