package com.lawu.eshop.mall.param;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author zhangyong
 * @date 2017/4/19.
 */
public class OperatorMessageInfoParam {
    @ApiModelProperty(value = "推送标题")
    private String title;
    @ApiModelProperty(value = "推送内容")
    private String content;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
