package com.lawu.eshop.user.dto.param;

import java.util.Date;

/**
 *
 *
 * Created by zhangyong on 2017/3/23.
 */
public class MerchantProfileParam {

    private String websiteUrl;

    private String taobaoUrl;

    private String tmallUrl;

    private String jdUrl;


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

}
