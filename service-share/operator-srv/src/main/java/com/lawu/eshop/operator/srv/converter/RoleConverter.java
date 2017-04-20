package com.lawu.eshop.operator.srv.converter;

import com.lawu.eshop.operator.srv.bo.RoleBO;
import com.lawu.eshop.operator.srv.domain.extend.UserRoleDOView;

/**
 * @author zhangyong
 * @date 2017/4/19.
 */
public class RoleConverter {

    public static RoleBO coverBO(UserRoleDOView userRoleDOView) {
        if(userRoleDOView == null){
            return null;
        }
        RoleBO roleBO = new RoleBO();
        roleBO.setRoleName(userRoleDOView.getRoleName());
        roleBO.setRoleKey(userRoleDOView.getRoleKey());
        roleBO.setId(userRoleDOView.getId());
        return roleBO;
    }

}
