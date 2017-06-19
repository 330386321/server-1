package com.lawu.eshop.user.srv.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawu.eshop.user.srv.bo.MemberProfileBO;
import com.lawu.eshop.user.srv.converter.MemberProfileConverter;
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
		if(memberProfile==null){
			return 0;
		}else{
			return memberProfile.getInviteMemberCount();
		}
		
	}

	@Override
	public Integer getMerchantCount(Long id) {
		MemberProfileDO memberProfile = memberProfileDOMapper.selectByPrimaryKey(id);
		if(memberProfile==null){
			return 0;
		}else{
			return memberProfile.getInviteMerchantCount();
		}
		
	}

	/**
	 * 根据会员id 查询会员扩展信息
	 * @param id
	 * @return
	 */
	@Override
	public MemberProfileBO get(Long id) {
		MemberProfileDO memberProfileDO = memberProfileDOMapper.selectByPrimaryKey(id);
		MemberProfileBO memberProfileBO = MemberProfileConverter.convert(memberProfileDO);
		memberProfileDO = null;
		return memberProfileBO;
	}

}
