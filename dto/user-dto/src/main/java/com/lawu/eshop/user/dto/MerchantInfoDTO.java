package com.lawu.eshop.user.dto;

import io.swagger.annotations.ApiParam;

import java.util.Date;

/**
 * 商家扩展信息表
 * Created by zhangyong on 2017/3/23.
 */
public class MerchantInfoDTO {


    /**
     * 邀请会员数
     */
    @ApiParam(name = "inviteMemberCount",value = "邀请会员数")
    private Integer inviteMemberCount;

    /**
     * 邀请商家数
     */
    @ApiParam(name = "inviteMerchantCount",value = "邀请商家数")
    private Integer inviteMerchantCount;

    /**
     * 头像
     */
    @ApiParam(name = "headimg",value = "头像")
    private String headimg;

    /**
     * 账号
     */
    @ApiParam(name = "account",value = "账号")
    private String account;

    /**
     * 负责人名字
     */
    @ApiParam(name = "principalName ",value = "负责人名字")
    private String principalName;

    public Integer getInviteMemberCount() {
        return inviteMemberCount;
    }

    public void setInviteMemberCount(Integer inviteMemberCount) {
        this.inviteMemberCount = inviteMemberCount;
    }

    public Integer getInviteMerchantCount() {
        return inviteMerchantCount;
    }

    public void setInviteMerchantCount(Integer inviteMerchantCount) {
        this.inviteMerchantCount = inviteMerchantCount;
    }

    public String getHeadimg() {
        return headimg;
    }

    public void setHeadimg(String headimg) {
        this.headimg = headimg;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getPrincipalName() {
        return principalName;
    }

    public void setPrincipalName(String principalName) {
        this.principalName = principalName;
    }
}
