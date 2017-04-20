package com.lawu.eshop.operator.srv.service.impl;

import com.lawu.eshop.operator.param.PerssionParam;
import com.lawu.eshop.operator.srv.domain.PermissionDO;
import com.lawu.eshop.operator.srv.mapper.PermissionDOMapper;
import com.lawu.eshop.operator.srv.service.PermissonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author zhangyong
 * @date 2017/4/20.
 */
@Service
public class PermissonServiceImpl implements PermissonService {
    @Autowired
    private PermissionDOMapper permissionDOMapper;
    @Override
    public Integer addPerssion(PerssionParam perssionParam) {
        PermissionDO permissionDO = new PermissionDO();
        permissionDO.setParentId(perssionParam.getParentId());
        permissionDO.setPermissionKey(perssionParam.getPermissionKey());
        permissionDO.setPermissionName(perssionParam.getPermissionName());
        permissionDO.setPermissionUrl(perssionParam.getPermissionUrl());
        permissionDO.setGmtCreate(new Date());
        permissionDO.setGmtModified(new Date());
        Integer row = permissionDOMapper.insert(permissionDO);
        return row;
    }
}
