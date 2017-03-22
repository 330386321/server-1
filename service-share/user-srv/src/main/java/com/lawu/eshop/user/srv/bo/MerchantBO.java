package com.lawu.eshop.user.srv.bo;

import com.sun.org.apache.xpath.internal.operations.String;

/**
 * @author meishuquan
 * @date 2017/3/22
 */
public class MerchantBO {

    private Long id;

    private String num;

    private String account;

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
}
