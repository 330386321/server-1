package com.lawu.eshop.mall.param;

import com.lawu.eshop.mall.constants.UserTypeEnum;

import java.util.List;

/**
 * @author zhangyong
 * @date 2017/4/24.
 */
public class PushListParam {

    private List<PushParam> list;

    private UserTypeEnum userTypeEnum;

    public List<PushParam> getList() {
        return list;
    }

    public void setList(List<PushParam> list) {
        this.list = list;
    }

    public UserTypeEnum getUserTypeEnum() {
        return userTypeEnum;
    }

    public void setUserTypeEnum(UserTypeEnum userTypeEnum) {
        this.userTypeEnum = userTypeEnum;
    }
}
