package com.lawu.eshop.user.dto.param;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author zhangyong
 * @date 2017/4/10.
 */
public class MerchantSizeLinkDTO {

    @ApiModelProperty(value = "官网链接")
    private String websiteUrl;

    @ApiModelProperty(value = "淘宝链接")
    private String taobaoUrl;

    @ApiModelProperty(value = "天猫链接")
    private String tmallUrl;
    
    @ApiModelProperty(value = "京东链接")
    private String jdUrl;

    public String getWebsiteUrl() {
        return websiteUrl;
    }

    public void setWebsiteUrl(String websiteUrl) {
        this.websiteUrl = websiteUrl;
    }

    public String getTaobaoUrl() {
        return taobaoUrl;
    }

    public void setTaobaoUrl(String taobaoUrl) {
        this.taobaoUrl = taobaoUrl;
    }

    public String getTmallUrl() {
        return tmallUrl;
    }

    public void setTmallUrl(String tmallUrl) {
        this.tmallUrl = tmallUrl;
    }

    public String getJdUrl() {
        return jdUrl;
    }

    public void setJdUrl(String jdUrl) {
        this.jdUrl = jdUrl;
    }
}
