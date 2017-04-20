package com.lawu.eshop.operator.srv.service;

import com.lawu.eshop.operator.srv.bo.RoleBO;
import com.lawu.eshop.operator.srv.bo.UserBO;

import java.util.List;

/**
 * @author zhangyong
 * @date 2017/4/19.
 */
public interface UserService {

    UserBO find(String account,String pwd);

    List<RoleBO> findUserRoleByUserId(Integer userId);

    List<String> findRolePermission(Integer roleId);

    UserBO find(String account);
}
