package com.lawu.eshop.framework.web.dto;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author Leach
 * @date 2017/3/14
 */
public class TokenDTO {

    @ApiModelProperty(value = "用户编号", required = true)
    private String userNum;

    @ApiModelProperty(value = "用户token，登录后每次请求必须带上该标志", required = true)
    private String token;

    public String getUserNum() {
        return userNum;
    }

    public void setUserNum(String userNum) {
        this.userNum = userNum;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
