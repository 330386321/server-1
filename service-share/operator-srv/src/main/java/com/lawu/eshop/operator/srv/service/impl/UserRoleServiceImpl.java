package com.lawu.eshop.operator.srv.service.impl;

import com.lawu.eshop.operator.srv.bo.UserRoleBO;
import com.lawu.eshop.operator.srv.converter.UserRoleCoverter;
import com.lawu.eshop.operator.srv.domain.UserRoleDO;
import com.lawu.eshop.operator.srv.domain.UserRoleDOExample;
import com.lawu.eshop.operator.srv.mapper.UserRoleDOMapper;
import com.lawu.eshop.operator.srv.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author zhangyong
 * @date 2017/4/20.
 */
@Service
public class UserRoleServiceImpl implements UserRoleService {
    @Autowired
    private UserRoleDOMapper userRoleDOMapper;

    @Override
    public UserRoleBO findUserRoleInfo(Integer userId, Integer roleId) {
        UserRoleDOExample example = new UserRoleDOExample();
        example.createCriteria().andUserIdEqualTo(userId).andRoleIdEqualTo(roleId);
        List<UserRoleDO> userRoleDOList = userRoleDOMapper.selectByExample(example);
        if (userRoleDOList.isEmpty()) {
            return null;
        }
        UserRoleBO userRoleBO = UserRoleCoverter.cover(userRoleDOList.get(0));
        return userRoleBO;
    }

    @Override
    @Transactional
    public Integer assignRoles(Integer userId, Integer roleId) {
        UserRoleDO userRoleDO = new UserRoleDO();
        userRoleDO.setUserId(userId);
        userRoleDO.setRoleId(roleId);
        userRoleDO.setGmtCreate(new Date());
        int row = userRoleDOMapper.insert(userRoleDO);
        return row;
    }
}
