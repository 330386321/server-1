package com.lawu.eshop.user.dto.param;

import java.util.Date;

/**
 * Created by zhangyong on 2017/3/23.
 */
public class MerchantProfileParam {

    private Long id;

    private Integer inviteMemberCount;

    private Integer inviteMerchantCount;

    private String websiteUrl;

    private String taobaoUrl;

    private String tmallUrl;

    private String jdUrl;

    private Date gmtModified;

    private Date gmtCreate;

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

    public Date getGmtModified() {
        return gmtModified;
    }

    public void setGmtModified(Date gmtModified) {
        this.gmtModified = gmtModified;
    }

    public Date getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Integer getInviteMerchantCount() {
        return inviteMerchantCount;
    }

    public void setInviteMerchantCount(Integer inviteMerchantCount) {
        this.inviteMerchantCount = inviteMerchantCount;
    }
}
