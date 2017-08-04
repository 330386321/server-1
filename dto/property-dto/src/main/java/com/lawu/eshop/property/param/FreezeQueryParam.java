package com.lawu.eshop.property.param;

import com.lawu.eshop.framework.core.page.AbstractPageParam;
import com.lawu.eshop.property.constants.UserTypeEnum;
import io.swagger.annotations.ApiModelProperty;

/**
 *
 */
public class FreezeQueryParam extends AbstractPageParam{

    @ApiModelProperty(value = "编号")
    private String userNum;

    public String getUserNum() {
        return userNum;
    }

    public void setUserNum(String userNum) {
        this.userNum = userNum;
    }

}
