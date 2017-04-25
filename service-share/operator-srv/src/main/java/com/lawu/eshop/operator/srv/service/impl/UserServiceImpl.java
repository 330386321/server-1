package com.lawu.eshop.operator.srv.service.impl;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.operator.constants.StatusEnum;
import com.lawu.eshop.operator.param.UserPageParam;
import com.lawu.eshop.operator.param.UserParam;
import com.lawu.eshop.operator.srv.bo.PerssionInfoListBO;
import com.lawu.eshop.operator.srv.bo.RoleBO;
import com.lawu.eshop.operator.srv.bo.UserBO;
import com.lawu.eshop.operator.srv.bo.UserListBO;
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
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

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
        example.createCriteria().andAccountEqualTo(account).
                andPasswordEqualTo(passwordStrategy.encode(pwd)).
                andStatusEqualTo(StatusEnum.STATUS_VALID.val);
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
        example.createCriteria().andAccountEqualTo(account).andStatusEqualTo(StatusEnum.STATUS_VALID.val);
        List<UserDO> userDOS = userDOMapper.selectByExample(example);
        return userDOS.isEmpty() ? null : UserConverter.convertBO(userDOS.get(0));
    }

    @Override
    public List<PerssionInfoListBO> findRolePermissionList(Integer userId) {

        List<UserRoleDOView> userRoleDOViews = userRoleDOMapperExtend.findUserRoleByUserId(userId);
        if (userRoleDOViews.isEmpty()) {
            return null;
        }
        List<Map<String, String>> list = new ArrayList<>();
        List<Integer> roleids = new ArrayList<>();
        for (UserRoleDOView userRoleDOView : userRoleDOViews) {
            roleids.add(userRoleDOView.getId());
        }
        List<RolePermissionDOView> rolePermissionDOViews = rolePermissionDOMapperExtend.findRolePermissionList(roleids);
        if(rolePermissionDOViews.isEmpty()){
            return null;
        }
        List<PerssionInfoListBO> listBOS = new ArrayList<>();
        for (RolePermissionDOView rolePermissionDOView :rolePermissionDOViews){
            PerssionInfoListBO perssionInfoListBO = new PerssionInfoListBO();
            perssionInfoListBO.setId(rolePermissionDOView.getId());
            perssionInfoListBO.setParentId(rolePermissionDOView.getParentId());
            perssionInfoListBO.setPermissionKey(rolePermissionDOView.getPermissionKey());
            perssionInfoListBO.setPermissionName(rolePermissionDOView.getPermissionName());
            perssionInfoListBO.setPermissionUrl(rolePermissionDOView.getPermissionUrl());
            listBOS.add(perssionInfoListBO);
        }
        /*perssionInfoListBO.setPerssionInfo(set);*/
        return listBOS;
    }

    @Override
    @Transactional
    public Integer addUser(String account, String name, String password) {
        UserDO userDO = new UserDO();
        userDO.setAccount(account);
        userDO.setPassword(passwordStrategy.encode(password));
        userDO.setName(name);
        userDO.setStatus(StatusEnum.STATUS_VALID.val);
        userDO.setGmtCreate(new Date());
        userDO.setGmtModified(new Date());
        userDOMapper.insert(userDO);
        return userDO.getId();
    }

    @Override
    @Transactional
    public Integer editUser(UserParam userParam) {
        UserDO userDO = new UserDO();
        userDO.setAccount(userParam.getAccount());
        userDO.setPassword(passwordStrategy.encode(userParam.getPassword()));
        userDO.setName(userParam.getName());
        userDO.setGmtModified(new Date());
        userDO.setId(userParam.getId());
        Integer row = userDOMapper.updateByPrimaryKeySelective(userDO);
        return row;
    }

    @Override
    public Page<UserListBO> findUserList(UserPageParam pageParam) {
        UserDOExample example = new UserDOExample();
        example.createCriteria().andStatusNotEqualTo(StatusEnum.STATUS_INVALID.val);
        example.setOrderByClause("id desc");
        RowBounds rowBounds = new RowBounds(pageParam.getOffset(), pageParam.getPageSize());
        int count = userDOMapper.countByExample(example);

        List<UserDO> userDOS = userDOMapper.selectByExampleWithRowbounds(example, rowBounds);
        if (userDOS.isEmpty()) {
            return null;
        }
        List<UserListBO> listBOS = new ArrayList<>();
        for (UserDO userDO : userDOS) {
            UserListBO userListBO = UserConverter.cover(userDO);
            listBOS.add(userListBO);
        }
        Page<UserListBO> page = new Page<>();
        page.setCurrentPage(pageParam.getCurrentPage());
        page.setTotalCount(count);
        page.setRecords(listBOS);
        return page;
    }

    @Override
    @Transactional
    public void delUser(Integer id) {
        UserDO userDO = new UserDO();
        userDO.setId(id);
        userDO.setStatus(StatusEnum.STATUS_INVALID.val);
        userDOMapper.updateByPrimaryKeySelective(userDO);
    }

    @Override
    @Transactional
    public Integer userDisabled(Integer id) {
        UserDO userDO = new UserDO();
        userDO.setId(id);
        userDO.setStatus(StatusEnum.STATUS_DISABLE.val);
       int row =  userDOMapper.updateByPrimaryKeySelective(userDO);
        return row;
    }
}
