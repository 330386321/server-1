package com.lawu.eshop.ad.srv.service.impl;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawu.eshop.ad.constants.PlatformRedPacketStatusEnum;
import com.lawu.eshop.ad.param.PlatformRedPacketParam;
import com.lawu.eshop.ad.param.PlatformRedPacketQueryParam;
import com.lawu.eshop.ad.srv.bo.PlatformRedPacketBO;
import com.lawu.eshop.ad.srv.converter.PlatformRedPacketConverter;
import com.lawu.eshop.ad.srv.domain.PlatformRedPacketDO;
import com.lawu.eshop.ad.srv.domain.PlatformRedPacketDOExample;
import com.lawu.eshop.ad.srv.mapper.PlatformRedPacketDOMapper;
import com.lawu.eshop.ad.srv.service.PlatformRedPacketService;
import com.lawu.eshop.framework.core.page.Page;

@Service
public class PlatformRedPacketServiceImpl implements PlatformRedPacketService {
	
	@Autowired
	private PlatformRedPacketDOMapper platformRedPacketDOMapper;

	@Override
	public void saveRedPacket(PlatformRedPacketParam param) {
		//设置红包时，将已经存在启用的设置为禁用
		PlatformRedPacketDO updateDO = new PlatformRedPacketDO();
		updateDO.setAuditorId(param.getAuditorId());
		updateDO.setStatus(PlatformRedPacketStatusEnum.DISENABLE.val);
		PlatformRedPacketDOExample example = new PlatformRedPacketDOExample();
		example.createCriteria().andStatusEqualTo(PlatformRedPacketStatusEnum.ENABLE.val);
		platformRedPacketDOMapper.updateByExampleSelective(updateDO, example);
		
		PlatformRedPacketDO record = new PlatformRedPacketDO();
		record.setAuditorId(param.getAuditorId());
		record.setGmtCreate(new Date());
		record.setGmtModified(new Date());
		record.setMoney(param.getMoney());
		record.setSendCount(0);
		record.setStatus(PlatformRedPacketStatusEnum.ENABLE.val);
		platformRedPacketDOMapper.insert(record);
		
	}

	@Override
	public void setRedPacket(Long id , Long auditorId) {
		
		PlatformRedPacketDO record = new PlatformRedPacketDO();
		record.setId(id);
		record.setAuditorId(auditorId);
		record.setGmtModified(new Date());
		record.setStatus(PlatformRedPacketStatusEnum.DISENABLE.val);
		platformRedPacketDOMapper.updateByPrimaryKeySelective(record);
		
	}

	@Override
	public Page<PlatformRedPacketBO> queryRedPacket(PlatformRedPacketQueryParam query) {
		
		PlatformRedPacketDOExample example = new PlatformRedPacketDOExample();
		example.setOrderByClause("gmt_create desc");
		Long count = platformRedPacketDOMapper.countByExample(example);
		RowBounds rowBounds = new RowBounds(query.getOffset(), query.getPageSize());
		List<PlatformRedPacketDO> list = platformRedPacketDOMapper.selectByExampleWithRowbounds(example, rowBounds);
		List<PlatformRedPacketBO> bos =PlatformRedPacketConverter.convertBOS(list);
		Page<PlatformRedPacketBO> page = new Page<PlatformRedPacketBO>();
		page.setCurrentPage(query.getCurrentPage());
		page.setTotalCount(count.intValue());
		page.setRecords(bos);
		return page;
		
	}

}
