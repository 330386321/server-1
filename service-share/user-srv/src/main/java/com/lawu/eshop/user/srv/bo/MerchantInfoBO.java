package com.lawu.eshop.user.srv.bo;

/**
 * 商家主页信息
 * Created by Administrator on 2017/3/24.
 */
public class MerchantInfoBO {
    /**
     * 商家账号
     */
    private String account;

    /**
     * 商家头像
     */
    private String headimg;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getHeadimg() {
        return headimg;
    }

    public void setHeadimg(String headimg) {
        this.headimg = headimg;
    }
}
