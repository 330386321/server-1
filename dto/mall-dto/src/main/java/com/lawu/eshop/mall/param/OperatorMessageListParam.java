package com.lawu.eshop.mall.param;

import com.lawu.eshop.mall.constants.UserTypeEnum;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author zhangyong
 * @date 2017/4/24.
 */
public class OperatorMessageListParam {
    @ApiModelProperty(value = "推送标题",required = true)
    private String title;
    @ApiModelProperty(value = "推送内容",required = true)
    private String content;

    @ApiModelProperty(value = "推送用户类型",required = true)
    private UserTypeEnum userTypeEnum;
    @ApiModelProperty(value = "区域")
    private String area;

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

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }
}
