package com.lawu.eshop.operator.srv.service.impl;

import com.lawu.eshop.operator.srv.bo.RoleBO;
import com.lawu.eshop.operator.srv.bo.UserBO;
import com.lawu.eshop.operator.srv.converter.RoleConverter;
import com.lawu.eshop.operator.srv.converter.UserConverter;
import com.lawu.eshop.operator.srv.domain.UserDO;
import com.lawu.eshop.operator.srv.domain.UserDOExample;
import com.lawu.eshop.operator.srv.domain.extend.RolePermissionDOView;
import com.lawu.eshop.operator.srv.domain.extend.UserRoleDOView;
import com.lawu.eshop.operator.srv.mapper.UserDOMapper;
import com.lawu.eshop.operator.srv.mapper.extend.RolePermissionDOMapperExtend;
import com.lawu.eshop.operator.srv.mapper.extend.UserRoleDOMapperExtend;
import com.lawu.eshop.operator.srv.service.UserService;
import com.lawu.eshop.operator.srv.strategy.PasswordStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangyong
 * @date 2017/4/19.
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDOMapper userDOMapper;
    @Autowired
    private UserRoleDOMapperExtend userRoleDOMapperExtend;

    @Autowired
    private RolePermissionDOMapperExtend rolePermissionDOMapperExtend;

    @Autowired
    private PasswordStrategy passwordStrategy;


    @Override
    public UserBO find(String account, String pwd) {
        UserDOExample example = new UserDOExample();
        example.createCriteria().andAccountEqualTo(account).andPasswordEqualTo(passwordStrategy.encode(pwd));
        List<UserDO> userDOS = userDOMapper.selectByExample(example);
        return userDOS.isEmpty() ? null : UserConverter.convertBO(userDOS.get(0));
    }

    @Override
    public List<RoleBO> findUserRoleByUserId(Integer userId) {

        List<UserRoleDOView> userRoleDOViews = userRoleDOMapperExtend.findUserRoleByUserId(userId);
        if (userRoleDOViews.isEmpty()) {
            return null;
        }
        List<RoleBO> roleBOS = new ArrayList<>();
        for (UserRoleDOView userRoleDOView : userRoleDOViews) {
            RoleBO roleBO = RoleConverter.coverBO(userRoleDOView);
            roleBOS.add(roleBO);
        }
        return roleBOS;
    }

    @Override
    public List<String> findRolePermission(Integer roleId) {
        List<RolePermissionDOView> rolePermissionDOViews = rolePermissionDOMapperExtend.findRolePermission(roleId);
        if (rolePermissionDOViews.isEmpty()) {
            return null;
        }
        List<String> permissionKeys = new ArrayList<>();
        for (RolePermissionDOView rolePermissionDOView : rolePermissionDOViews) {
            permissionKeys.add(rolePermissionDOView.getPermissionKey());
        }
        return permissionKeys;
    }

    @Override
    public UserBO find(String account) {
        UserDOExample example = new UserDOExample();
        example.createCriteria().andAccountEqualTo(account);
        List<UserDO> userDOS = userDOMapper.selectByExample(example);
        return userDOS.isEmpty() ? null : UserConverter.convertBO(userDOS.get(0));
    }
}
