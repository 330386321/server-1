package com.lawu.eshop.user.srv.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawu.eshop.user.srv.domain.MemberProfileDO;
import com.lawu.eshop.user.srv.mapper.MemberProfileDOMapper;
import com.lawu.eshop.user.srv.service.MemberProfileService;

@Service
public class MemberProfileServiceImpl implements MemberProfileService {
	
	@Autowired
	private MemberProfileDOMapper memberProfileDOMapper; 

	@Override
	public Integer getMemberCount(Long id) {
		MemberProfileDO memberProfile = memberProfileDOMapper.selectByPrimaryKey(id);
		return memberProfile.getInviteMemberCount();
	}

	@Override
	public Integer getMerchantCount(Long id) {
		MemberProfileDO memberProfile = memberProfileDOMapper.selectByPrimaryKey(id);
		return memberProfile.getInviteMerchantCount();
	}

}
