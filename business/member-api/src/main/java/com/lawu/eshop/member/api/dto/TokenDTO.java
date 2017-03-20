package com.lawu.eshop.member.api.dto;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author Leach
 * @date 2017/3/14
 */
public class TokenDTO {

    @ApiModelProperty(value = "用户token，登录后每次请求必须带上该标志", required = true)
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
