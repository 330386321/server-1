package com.lawu.eshop.operator.srv.service;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.operator.param.UserPageParam;
import com.lawu.eshop.operator.param.UserParam;
import com.lawu.eshop.operator.srv.bo.PerssionInfoListBO;
import com.lawu.eshop.operator.srv.bo.RoleBO;
import com.lawu.eshop.operator.srv.bo.UserBO;
import com.lawu.eshop.operator.srv.bo.UserListBO;

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

    PerssionInfoListBO findRolePermissionList(Integer id);

    Integer addUser(String account, String name, String password);

    Integer editUser(UserParam userParam);

    Page<UserListBO> findUserList(UserPageParam pageParam);

    void delUser(Integer id);

    Integer userDisabled(Integer id);
}
