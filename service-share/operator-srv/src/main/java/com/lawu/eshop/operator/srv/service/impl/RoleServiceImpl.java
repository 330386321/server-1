package com.lawu.eshop.operator.srv.service.impl;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.operator.param.RoleParam;
import com.lawu.eshop.operator.srv.bo.RoleBO;
import com.lawu.eshop.operator.srv.converter.RoleConverter;
import com.lawu.eshop.operator.srv.domain.RoleDO;
import com.lawu.eshop.operator.srv.domain.RoleDOExample;
import com.lawu.eshop.operator.srv.mapper.RoleDOMapper;
import com.lawu.eshop.operator.srv.service.RoleService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhangyong
 * @date 2017/4/21.
 */
@Service
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleDOMapper roleDOMapper;

    @Override
    public Page<RoleBO> findroleList(RoleParam param) {

        RoleDOExample example = new RoleDOExample();
        example.setOrderByClause("id desc");
        RowBounds bounds = new RowBounds(param.getOffset(), param.getPageSize());

        int total = roleDOMapper.countByExample(example);

        List<RoleDO> roleDOList = roleDOMapper.selectByExampleWithRowbounds(example, bounds);
        if (roleDOList.isEmpty()) {
            return null;
        }
        List<RoleBO> list = new ArrayList<>();
        for (RoleDO roleDO : roleDOList) {
            RoleBO roleBO = RoleConverter.cover(roleDO);
            list.add(roleBO);
        }
        Page<RoleBO> page = new Page<>();
        page.setCurrentPage(param.getCurrentPage());
        page.setTotalCount(total);
        page.setRecords(list);
        return page;
    }
}
