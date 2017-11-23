package com.lawu.eshop.product.srv.service.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.product.constant.ActivityStatusEnum;
import com.lawu.eshop.product.param.SeckillActivityJoinParam;
import com.lawu.eshop.product.srv.bo.SeckillActivityJoinBO;
import com.lawu.eshop.product.srv.converter.SeckillActivityJoinConverter;
import com.lawu.eshop.product.srv.domain.SeckillActivityDO;
import com.lawu.eshop.product.srv.domain.SeckillActivityDOExample;
import com.lawu.eshop.product.srv.mapper.SeckillActivityDOMapper;
import com.lawu.eshop.product.srv.service.SeckillActivityJoinService;

@Service
public class SeckillActivityJoinServiceImpl implements SeckillActivityJoinService {
	
	@Autowired
	private SeckillActivityDOMapper seckillActivityDOMapper;

	@Override
	public Page<SeckillActivityJoinBO> queryPage(SeckillActivityJoinParam param) {
		
		SeckillActivityDOExample example = new SeckillActivityDOExample();
		example.createCriteria().andActivityStatusEqualTo(ActivityStatusEnum.NOT_STARTED.getValue());
		 example.setOrderByClause("start_date asc");
		RowBounds rowBounds = new RowBounds(param.getOffset(), param.getPageSize());
		List<SeckillActivityDO> list = seckillActivityDOMapper.selectByExampleWithRowbounds(example, rowBounds);
		Page<SeckillActivityJoinBO> page = new Page<>();
		page.setCurrentPage(param.getCurrentPage());
		Long count = seckillActivityDOMapper.countByExample(example);
		page.setTotalCount(count.intValue());
		page.setRecords(SeckillActivityJoinConverter.seckillActivityJoinBOConverter(list));
		
		return page;
	}

}
