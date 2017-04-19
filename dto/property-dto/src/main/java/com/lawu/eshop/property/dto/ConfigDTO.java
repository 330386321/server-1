package com.lawu.eshop.property.dto;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author zhangyong
 * @date 2017/4/19.
 */
public class ConfigDTO {

    @ApiModelProperty(value = "视频路径前缀")
    private String videoPathUrl;

    @ApiModelProperty(value = "图片路径前缀")
    private String imagePathUrl;

    public String getImagePathUrl() {
        return imagePathUrl;
    }

    public void setImagePathUrl(String imagePathUrl) {
        this.imagePathUrl = imagePathUrl;
    }

    public String getVideoPathUrl() {
        return videoPathUrl;
    }

    public void setVideoPathUrl(String videoPathUrl) {
        this.videoPathUrl = videoPathUrl;
    }
}
