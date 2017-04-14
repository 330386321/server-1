package com.lawu.eshop.ad.srv.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawu.eshop.ad.srv.domain.MemberAdRecordDOExample;
import com.lawu.eshop.ad.srv.mapper.MemberAdRecordDOMapper;
import com.lawu.eshop.ad.srv.service.MemberAdRecordService;

@Service
public class MemberAdRecordServiceImpl implements MemberAdRecordService {
	
	@Autowired
	private MemberAdRecordDOMapper memberAdRecordDOMapper;

	@Override
	public boolean isClickToDay(Long memberId, Long adId) {
		MemberAdRecordDOExample example=new MemberAdRecordDOExample();
		example.createCriteria().andAdIdEqualTo(adId).andMemberIdEqualTo(memberId).andClickDateEqualTo(new Date());
		long count=memberAdRecordDOMapper.countByExample(example);
		if(count>0)
			return true;
		else
			return false;
		
	}

}
