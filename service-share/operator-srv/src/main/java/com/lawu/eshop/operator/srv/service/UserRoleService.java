package com.lawu.eshop.operator.srv.service;

import com.lawu.eshop.operator.srv.bo.UserRoleBO;

import java.util.List;

/**
 * @author zhangyong
 * @date 2017/4/20.
 */
public interface UserRoleService {
    UserRoleBO findUserRoleInfo(Integer userId, Integer roleId);

    void assignRoles(Integer userId, String roleId);

    List<UserRoleBO> findUserRoleByRoleId(Integer id);

    void addRolePermission(Integer roleId, String permissionIds);

    List<UserRoleBO> findRoleByUserId(Integer userId);
}
