package com.lawu.eshop.user.param;

import com.lawu.eshop.framework.core.page.AbstractPageParam;
import com.lawu.eshop.framework.core.type.UserType;
import io.swagger.annotations.ApiModelProperty;

/**
 * @author zhangyong
 * @date 2017/8/4.
 */
public class AccountParam extends AbstractPageParam{

    @ApiModelProperty(value = "账号")
    private String account;

    @ApiModelProperty(value = "用户类型")
    private UserType userType;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public UserType getUserType() {
        return userType;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }
}
