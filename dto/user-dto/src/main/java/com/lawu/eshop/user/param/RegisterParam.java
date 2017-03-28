package com.lawu.eshop.user.param;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author meishuquan
 * @date 2017/3/23
 */
public class RegisterParam {

    @ApiModelProperty(value = "注册账号", required = true)
    private String account;

    @ApiModelProperty(value = "密码", required = true)
    private String pwd;

    @ApiModelProperty(value = "邀请人ID", required = true)
    private Long inviterId;

    @ApiModelProperty(value = "邀请人类型", required = true)
    private Byte inviterType;

    @ApiModelProperty(value = "手机验证码ID", required = true)
    private Long smsId;

    @ApiModelProperty(value = "验证码", required = true)
    private String smsCode;

    public Long getSmsId() {
        return smsId;
    }

    public void setSmsId(Long smsId) {
        this.smsId = smsId;
    }

    public String getSmsCode() {
        return smsCode;
    }

    public void setSmsCode(String smsCode) {
        this.smsCode = smsCode;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }

    public Long getInviterId() {
        return inviterId;
    }

    public void setInviterId(Long inviterId) {
        this.inviterId = inviterId;
    }

    public Byte getInviterType() {
        return inviterType;
    }

    public void setInviterType(Byte inviterType) {
        this.inviterType = inviterType;
    }
}
