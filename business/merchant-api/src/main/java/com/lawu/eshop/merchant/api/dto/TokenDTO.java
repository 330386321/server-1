package com.lawu.eshop.merchant.api.dto;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author meishuquan
 * @date 2017/3/22
 */
public class TokenDTO {

    @ApiModelProperty(value = "商户token，登录后每次请求必须带上该标志", required = true)
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
