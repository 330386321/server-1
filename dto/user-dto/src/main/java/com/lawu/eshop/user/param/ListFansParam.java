package com.lawu.eshop.user.param;

import com.lawu.eshop.framework.core.page.AbstractPageParam;
import com.lawu.eshop.user.constants.UserSexEnum;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author meishuquan
 * @date 2017/4/6.
 */
public class ListFansParam extends AbstractPageParam {

    @ApiModelProperty(value = "性别", required = true)
    private UserSexEnum userSexEnum;

    @ApiModelProperty(value = "区域")
    private String regionPath;

    @ApiModelProperty(value = "最小年龄")
    private Integer startAge;

    @ApiModelProperty(value = "最大年龄")
    private Integer endAge;

    @ApiModelProperty(value = "排序类型：1--消费次数，2--消费额，3--时间")
    private Integer orderType;

    @ApiModelProperty(value = "排序规则：asc 或 desc")
    private String orderRule;

    public UserSexEnum getUserSexEnum() {
        return userSexEnum;
    }

    public void setUserSexEnum(UserSexEnum userSexEnum) {
        this.userSexEnum = userSexEnum;
    }

    public String getRegionPath() {
        return regionPath;
    }

    public void setRegionPath(String regionPath) {
        this.regionPath = regionPath;
    }

    public Integer getStartAge() {
        return startAge;
    }

    public void setStartAge(Integer startAge) {
        this.startAge = startAge;
    }

    public Integer getEndAge() {
        return endAge;
    }

    public void setEndAge(Integer endAge) {
        this.endAge = endAge;
    }

    public Integer getOrderType() {
        return orderType;
    }

    public void setOrderType(Integer orderType) {
        this.orderType = orderType;
    }

    public String getOrderRule() {
        return orderRule;
    }

    public void setOrderRule(String orderRule) {
        this.orderRule = orderRule;
    }
}
