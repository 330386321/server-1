package com.lawu.eshop.mall.param;

import com.lawu.eshop.mall.constants.UserTypeEnum;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author zhangyong
 * @date 2017/4/19.
 */
public class OperatorMessageInfoParam {
    @ApiModelProperty(value = "推送标题",required = true)
    private String title;
    @ApiModelProperty(value = "推送内容",required = true)
    private String content;

    private UserTypeEnum userTypeEnum;

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

    public UserTypeEnum getUserTypeEnum() {
        return userTypeEnum;
    }

    public void setUserTypeEnum(UserTypeEnum userTypeEnum) {
        this.userTypeEnum = userTypeEnum;
    }
}
