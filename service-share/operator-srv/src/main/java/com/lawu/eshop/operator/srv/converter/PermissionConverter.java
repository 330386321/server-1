package com.lawu.eshop.operator.srv.converter;

import com.lawu.eshop.operator.srv.bo.PermissionBO;
import com.lawu.eshop.operator.srv.domain.extend.RolePermissionDOView;

/**
 * @author zhangyong
 * @date 2017/4/19.
 */
public class PermissionConverter {
    public static PermissionBO coverBO(RolePermissionDOView rolePermissionDOView) {
        if(rolePermissionDOView == null){
            return null;
        }
        PermissionBO permissionBO = new PermissionBO();
        permissionBO.setPermissionName(rolePermissionDOView.getPermissionName());
        permissionBO.setPermissionKey(rolePermissionDOView.getPermissionKey());
        permissionBO.setId(rolePermissionDOView.getId());
        return  permissionBO;
    }
}
