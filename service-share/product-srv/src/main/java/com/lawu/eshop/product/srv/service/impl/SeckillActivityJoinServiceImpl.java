package com.lawu.eshop.product.srv.service.impl;

import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.product.constant.ActivityStatusEnum;
import com.lawu.eshop.product.param.SeckillActivityJoinParam;
import com.lawu.eshop.product.param.SeckillActivityManageParam;
import com.lawu.eshop.product.srv.bo.SeckillActivityDetailBO;
import com.lawu.eshop.product.srv.bo.SeckillActivityJoinBO;
import com.lawu.eshop.product.srv.bo.SeckillActivityManageBO;
import com.lawu.eshop.product.srv.converter.SeckillActivityJoinConverter;
import com.lawu.eshop.product.srv.domain.SeckillActivityDO;
import com.lawu.eshop.product.srv.domain.SeckillActivityDOExample;
import com.lawu.eshop.product.srv.domain.SeckillActivityProductDOExample;
import com.lawu.eshop.product.srv.domain.extend.SeckillActivityDOView;
import com.lawu.eshop.product.srv.mapper.SeckillActivityDOMapper;
import com.lawu.eshop.product.srv.mapper.SeckillActivityProductDOMapper;
import com.lawu.eshop.product.srv.mapper.extend.SeckillActivityDOMapperExtend;
import com.lawu.eshop.product.srv.service.SeckillActivityJoinService;

@Service
public class SeckillActivityJoinServiceImpl implements SeckillActivityJoinService {
	
	@Autowired
	private SeckillActivityDOMapper seckillActivityDOMapper;
	
	@Autowired
	private SeckillActivityDOMapperExtend seckillActivityDOMapperExtend;
	
	@Autowired
	private SeckillActivityProductDOMapper seckillActivityProductDOMapper;

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

	@Override
	public Page<SeckillActivityManageBO> queryManagePage(SeckillActivityManageParam param) {
		
		RowBounds rowBounds = new RowBounds(param.getOffset(), param.getPageSize());
		List<SeckillActivityDOView> list = seckillActivityDOMapperExtend.queryManagePage(param.getMerchantId(), rowBounds);
		Page<SeckillActivityManageBO> page = new Page<>();
		page.setCurrentPage(param.getCurrentPage());
		int count = seckillActivityDOMapperExtend.countManage(param.getMerchantId());
		page.setTotalCount(count);
		page.setRecords(SeckillActivityJoinConverter.seckillActivityJoinManageBOConverter(list));
		
		return page;
	}

	@Override
	public SeckillActivityDetailBO querySeckillActivityDetail(Long id,Long merchantId) {
		
		SeckillActivityDO seckillActivityDO = seckillActivityDOMapper.selectByPrimaryKey(id);
		SeckillActivityDetailBO seckillActivityDetailBO = SeckillActivityJoinConverter.seckillActivityJoinDetailBOConverter(seckillActivityDO);
		SeckillActivityProductDOExample example = new SeckillActivityProductDOExample();
		example.createCriteria().andActivityIdEqualTo(id).andMerchantIdEqualTo(merchantId);
		Long joinCount = seckillActivityProductDOMapper.countByExample(example);
		seckillActivityDetailBO.setJoinCount(joinCount.intValue());
		
		return seckillActivityDetailBO;
	}

}
