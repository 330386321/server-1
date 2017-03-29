package com.lawu.eshop.mall.dto;

/**
 * @author meishuquan
 * @date 2017/3/28.
 */
public class VerifyCodeDTO {

    private Long id;

    private String mobile;

    private String code;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
