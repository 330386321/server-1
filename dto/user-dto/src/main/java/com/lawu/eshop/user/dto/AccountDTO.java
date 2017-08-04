package com.lawu.eshop.user.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.lawu.eshop.framework.core.type.UserType;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author zhangyong
 * @date 2017/8/4.
 */
public class AccountDTO {

    @ApiModelProperty(value = "账户ID")
    private Long id;

    @ApiModelProperty(value = "账户Num")
    private String num ;

    @ApiModelProperty(value = "账号")
    private String account;

    @ApiModelProperty(value = "用户类型")
    private UserType userType;

    @ApiModelProperty(value = "是否冻结")
    private Boolean isFreeze;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date gmtCreate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

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

    public Boolean getFreeze() {
        return isFreeze;
    }

    public void setFreeze(Boolean freeze) {
        isFreeze = freeze;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }
}
