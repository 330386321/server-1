package com.lawu.eshop.user.param;

import com.lawu.eshop.user.constants.UserSexEnum;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author meishuquan
 * @date 2017/4/6.
 */
public class InviteFansParam {

    @ApiModelProperty(value = "性别",required = true)
    private UserSexEnum userSexEnum;

    @ApiModelProperty(value = "区域")
    private String regionPath;

    @ApiModelProperty(value = "最小年龄")
    private Integer startAge;

    @ApiModelProperty(value = "最大年龄")
    private Integer endAge;

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
}
