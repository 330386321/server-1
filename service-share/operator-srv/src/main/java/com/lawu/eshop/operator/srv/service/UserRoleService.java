package com.lawu.eshop.operator.srv.service;

import com.lawu.eshop.operator.srv.bo.UserRoleBO;

/**
 * @author zhangyong
 * @date 2017/4/20.
 */
public interface UserRoleService {
    UserRoleBO findUserRoleInfo(Integer userId, Integer roleId);

    Integer assignRoles(Integer userId, Integer roleId);
}
