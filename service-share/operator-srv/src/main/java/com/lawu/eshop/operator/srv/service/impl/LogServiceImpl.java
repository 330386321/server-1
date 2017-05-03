package com.lawu.eshop.operator.srv.service.impl;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.operator.constants.ModuleEnum;
import com.lawu.eshop.operator.constants.OperationTypeEnum;
import com.lawu.eshop.operator.param.ListLogParam;
import com.lawu.eshop.operator.param.LogParam;
import com.lawu.eshop.operator.srv.bo.LogBO;
import com.lawu.eshop.operator.srv.converter.LogConverter;
import com.lawu.eshop.operator.srv.domain.LogDO;
import com.lawu.eshop.operator.srv.domain.LogDOExample;
import com.lawu.eshop.operator.srv.mapper.LogDOMapper;
import com.lawu.eshop.operator.srv.service.LogService;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * @author meishuquan
 * @date 2017/5/3.
 */
@Service
public class LogServiceImpl implements LogService {

    @Autowired
    private LogDOMapper logDOMapper;

    @Override
    public void saveLog(LogParam logParam) {
        LogDO logDO = new LogDO();
        logDO.setAccount(logParam.getAccount());
        logDO.setOperationType(logParam.getTypeEnum().val);
        logDO.setModule(logParam.getModuleEnum().val);
        logDO.setBusinessId(logParam.getBusinessId());
        logDO.setChangeTitle(logParam.getChangeTitle());
        logDO.setChangeContent(logParam.getChangeContent());
        logDO.setGmtCreate(new Date());
        logDOMapper.insertSelective(logDO);
    }

    @Override
    public LogBO getLogById(Long id) {
        LogDO logDO = logDOMapper.selectByPrimaryKey(id);
        return LogConverter.convertBO(logDO);
    }

    @Override
    public Page<LogBO> listLog(ListLogParam listLogParam) {
        LogDOExample example = new LogDOExample();
        LogDOExample.Criteria criteria = example.createCriteria();
        if (listLogParam.getTypeEnum().val != OperationTypeEnum.ALL.val) {
            criteria.andOperationTypeEqualTo(listLogParam.getTypeEnum().val);
        }
        if (listLogParam.getModuleEnum().val != ModuleEnum.ALL.val) {
            criteria.andModuleEqualTo(listLogParam.getModuleEnum().val);
        }
        if (StringUtils.isNotEmpty(listLogParam.getSortName()) && StringUtils.isNotEmpty(listLogParam.getSortOrder())) {
            example.setOrderByClause("gmt_create " + listLogParam.getSortOrder());
        }

        RowBounds rowBounds = new RowBounds(listLogParam.getOffset(), listLogParam.getPageSize());

        Page<LogBO> page = new Page<>();
        page.setCurrentPage(listLogParam.getCurrentPage());
        page.setTotalCount((int) logDOMapper.countByExample(example));

        List<LogDO> logDOList = logDOMapper.selectByExampleWithRowbounds(example, rowBounds);
        page.setRecords(LogConverter.convertBO(logDOList));
        return page;
    }
}
