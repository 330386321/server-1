package com.lawu.eshop.mall.srv.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.mall.param.DealWorkOrderParam;
import com.lawu.eshop.mall.param.WorkOrderParam;
import com.lawu.eshop.mall.query.WorkOrderQuery;
import com.lawu.eshop.mall.srv.bo.WorkOrderBO;
import com.lawu.eshop.mall.srv.converter.WorkOrderConverter;
import com.lawu.eshop.mall.srv.domain.WorkOrderDO;
import com.lawu.eshop.mall.srv.domain.WorkOrderDOExample;
import com.lawu.eshop.mall.srv.domain.WorkOrderDOExample.Criteria;
import com.lawu.eshop.mall.srv.mapper.WorkOrderDOMapper;
import com.lawu.eshop.mall.srv.service.WorkOrderService;

@Service
public class WorkOrderServiceImpl implements WorkOrderService{

	@Autowired
	private WorkOrderDOMapper workOrderDOMapper;
	
	
	@Override
	public Integer saveWorkOrder(WorkOrderParam param) {
		WorkOrderDO record = new WorkOrderDO();
		record.setAccount(param.getAccount());
		record.setContent(param.getContent());
		record.setGmtCreate(new Date());
		record.setName(param.getName());
		record.setUserNum(param.getUserNum());
		record.setType(param.getWorkOrderTypeEnum().val);
		int i = workOrderDOMapper.insertSelective(record);
		return i;
	}

	@Override
	public Integer updateWorkOrder(DealWorkOrderParam dealWorkOrderParam) {
		WorkOrderDO record = new WorkOrderDO();
		record.setId(dealWorkOrderParam.getId());
		Date date = new Date();
		record.setGmtDeal(date);
		record.setGmtModified(date);
		record.setAuditorId(dealWorkOrderParam.getAuditorId());
		record.setAuditorName(dealWorkOrderParam.getAuditorName());
		record.setStatus(dealWorkOrderParam.getWorkOrderStatusEnum().val);
		record.setReplyContent(dealWorkOrderParam.getReplyContent());
		return workOrderDOMapper.updateByPrimaryKeySelective(record);
	}

	
	@Override
	public Page<WorkOrderBO> selectWorkOrder(WorkOrderQuery workOrderQuery) {
		WorkOrderDOExample example = new WorkOrderDOExample();
		Criteria criteria = example.createCriteria();
		if(workOrderQuery.getWorkOrderStatusEnum() != null)
			criteria.andStatusEqualTo(workOrderQuery.getWorkOrderStatusEnum().val);
		if(workOrderQuery.getWorkOrderTypeEnum() != null)
			criteria.andTypeEqualTo(workOrderQuery.getWorkOrderTypeEnum().val);
		if(workOrderQuery.getSearchContent() != null && !"".equals(workOrderQuery.getSearchContent()))
			criteria.andAccountLike("%" + workOrderQuery.getSearchContent() + "%");
		int i = workOrderDOMapper.countByExample(example);
		RowBounds rowBounds = new RowBounds(workOrderQuery.getOffset(), workOrderQuery.getPageSize());
		
		List<WorkOrderDO> list = workOrderDOMapper.selectByExampleWithRowbounds(example, rowBounds);
		Page<WorkOrderBO> page = new Page<WorkOrderBO>();
		page.setTotalCount(i);
		page.setCurrentPage(workOrderQuery.getCurrentPage());
		page.setRecords(WorkOrderConverter.convertDoToBo(list));
		return page;
	}
}
