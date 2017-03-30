package com.lawu.eshop.user.param;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author meishuquan
 * @date 2017/3/29.
 */
public class UpdatePwdParam {

    @ApiModelProperty(value = "手机验证码ID", required = true)
    private Long verifyCodeId;

    @ApiModelProperty(value = "手机验证码", required = true)
    private String smsCode;

    @ApiModelProperty(value = "原始密码")
    private String originalPwd;

    @ApiModelProperty(value = "新密码", required = true)
    private String newPwd;

    @ApiModelProperty(value = "业务类型(1--忘记密码/设置密码，2--修改密码)", required = true)
    private Integer type;

    public Long getVerifyCodeId() {
        return verifyCodeId;
    }

    public void setVerifyCodeId(Long verifyCodeId) {
        this.verifyCodeId = verifyCodeId;
    }

    public String getOriginalPwd() {
        return originalPwd;
    }

    public void setOriginalPwd(String originalPwd) {
        this.originalPwd = originalPwd;
    }

    public String getNewPwd() {
        return newPwd;
    }

    public void setNewPwd(String newPwd) {
        this.newPwd = newPwd;
    }

    public String getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(String smsCode) {
        this.smsCode = smsCode;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
