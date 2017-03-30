package com.lawu.eshop.mall.param;

import com.lawu.eshop.framework.core.page.PageParam;

/**
 * 传值给mapper
 * Created by Administrator on 2017/3/30.
 */
public class MessageQueryParam extends PageParam {
    private String userNum;

    public String getUserNum() {
        return userNum;
    }

    public void setUserNum(String userNum) {
        this.userNum = userNum;
    }
}
