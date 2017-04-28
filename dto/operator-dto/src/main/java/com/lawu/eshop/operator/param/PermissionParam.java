package com.lawu.eshop.operator.param;

import com.lawu.eshop.framework.core.page.AbstractPageParam;

/**
 * @author zhangyong
 * @date 2017/4/21.
 */
public class PermissionParam extends AbstractPageParam {
    private String permissionName;

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }
}
