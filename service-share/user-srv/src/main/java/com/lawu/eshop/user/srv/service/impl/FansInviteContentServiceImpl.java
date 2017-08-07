package com.lawu.eshop.user.srv.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawu.eshop.user.param.FansInviteContentParam;
import com.lawu.eshop.user.srv.bo.FansInviteContentBO;
import com.lawu.eshop.user.srv.converter.FansInviteContentConverter;
import com.lawu.eshop.user.srv.domain.FansInviteContentDO;
import com.lawu.eshop.user.srv.mapper.FansInviteContentDOMapper;
import com.lawu.eshop.user.srv.service.FansInviteContentService;

@Service
public class FansInviteContentServiceImpl implements FansInviteContentService{

	
	@Autowired
	private FansInviteContentDOMapper fansInviteContentDOMapper;
	
	@Override
	public Long saveInviteContentService(FansInviteContentParam inviteContentParam) {
		FansInviteContentDO fansInviteContentDO = FansInviteContentConverter.converterFansInviteContentParam(inviteContentParam);
		Date date = new Date();
		fansInviteContentDO.setGmtCreate(date);
		fansInviteContentDO.setGmtModified(date);
		fansInviteContentDOMapper.insertSelective(fansInviteContentDO);
		return fansInviteContentDO.getId();
	}

	@Override
	public FansInviteContentBO selectInviteContentById(Long id) {
		FansInviteContentDO ficDo = fansInviteContentDOMapper.selectByPrimaryKey(id);
		FansInviteContentBO result = FansInviteContentConverter.converterDoToBo(ficDo);
		return result;
	}

}
