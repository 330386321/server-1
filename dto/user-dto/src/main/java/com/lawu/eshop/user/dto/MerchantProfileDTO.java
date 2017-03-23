package com.lawu.eshop.user.dto;

import java.util.Date;

/**
 * 商家扩展信息表
 * Created by zhangyong on 2017/3/23.
 */
public class MerchantProfileDTO {

    private Long id;

    private Integer inviteMemberCount;

    private Integer inviteMerchantCount;

    private String websiteUrl;

    private String taobaoUrl;

    private String tmallUrl;

    private String jdUrl;

    private String headimg;

    private String account;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getInviteMemberCount() {
        return inviteMemberCount;
    }

    public void setInviteMemberCount(Integer inviteMemberCount) {
        this.inviteMemberCount = inviteMemberCount;
    }

    public String getWebsiteUrl() {
        return websiteUrl;
    }

    public void setWebsiteUrl(String websiteUrl) {
        this.websiteUrl = websiteUrl;
    }

    public String getTaobaoUrl() {
        return taobaoUrl;
    }

    public void setTaobaoUrl(String taobaoUrl) {
        this.taobaoUrl = taobaoUrl;
    }

    public String getTmallUrl() {
        return tmallUrl;
    }

    public void setTmallUrl(String tmallUrl) {
        this.tmallUrl = tmallUrl;
    }

    public String getJdUrl() {
        return jdUrl;
    }

    public void setJdUrl(String jdUrl) {
        this.jdUrl = jdUrl;
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
}
