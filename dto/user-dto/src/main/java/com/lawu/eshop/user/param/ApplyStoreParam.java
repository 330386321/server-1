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

    private String name;

    private String principalName;

    private String principalMobile;

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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrincipalName() {
        return principalName;
    }

    public void setPrincipalName(String principalName) {
        this.principalName = principalName;
    }

    public String getPrincipalMobile() {
        return principalMobile;
    }

    public void setPrincipalMobile(String principalMobile) {
        this.principalMobile = principalMobile;
    }
}
