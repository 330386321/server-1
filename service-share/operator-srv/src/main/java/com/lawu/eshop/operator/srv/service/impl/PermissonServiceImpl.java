package com.lawu.eshop.operator.srv.service.impl;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.operator.param.PermissionParam;
import com.lawu.eshop.operator.param.PerssionParam;
import com.lawu.eshop.operator.srv.bo.PermissionBO;
import com.lawu.eshop.operator.srv.converter.PermissionConverter;
import com.lawu.eshop.operator.srv.domain.PermissionDO;
import com.lawu.eshop.operator.srv.domain.PermissionDOExample;
import com.lawu.eshop.operator.srv.mapper.PermissionDOMapper;
import com.lawu.eshop.operator.srv.service.PermissonService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author zhangyong
 * @date 2017/4/20.
 */
@Service
public class PermissonServiceImpl implements PermissonService {
    @Autowired
    private PermissionDOMapper permissionDOMapper;

    @Override
    @Transactional
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

    @Override
    public Page<PermissionBO> findPerminnionList(PermissionParam param) {
        PermissionDOExample example = new PermissionDOExample();
        example.setOrderByClause("id desc");
        RowBounds bounds = new RowBounds(param.getOffset(), param.getPageSize());
        int total = permissionDOMapper.countByExample(example);

        List<PermissionDO> list = permissionDOMapper.selectByExampleWithRowbounds(example, bounds);
        if (list.isEmpty()) {
            return null;
        }
        List<PermissionBO> permissionBOS = new ArrayList<>();
        for (PermissionDO p : list) {
            PermissionBO permissionBO = PermissionConverter.cover(p);
            permissionBOS.add(permissionBO);
        }
        Page<PermissionBO> page = new Page<>();
        page.setCurrentPage(param.getCurrentPage());
        page.setTotalCount(total);
        page.setRecords(permissionBOS);
        return page;
    }
}
