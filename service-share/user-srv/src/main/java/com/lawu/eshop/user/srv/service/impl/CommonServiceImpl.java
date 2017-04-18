package com.lawu.eshop.user.srv.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawu.eshop.user.srv.bo.InviterBO;
import com.lawu.eshop.user.srv.converter.InviterConverter;
import com.lawu.eshop.user.srv.domain.InviteRelationDO;
import com.lawu.eshop.user.srv.domain.InviteRelationDOExample;
import com.lawu.eshop.user.srv.domain.MemberDO;
import com.lawu.eshop.user.srv.domain.MemberDOExample;
import com.lawu.eshop.user.srv.domain.MerchantDO;
import com.lawu.eshop.user.srv.domain.MerchantDOExample;
import com.lawu.eshop.user.srv.domain.MerchantStoreDO;
import com.lawu.eshop.user.srv.domain.MerchantStoreDOExample;
import com.lawu.eshop.user.srv.mapper.InviteRelationDOMapper;
import com.lawu.eshop.user.srv.mapper.MemberDOMapper;
import com.lawu.eshop.user.srv.mapper.MerchantDOMapper;
import com.lawu.eshop.user.srv.mapper.MerchantStoreDOMapper;
import com.lawu.eshop.user.srv.service.CommonService;

/**
 * 公共服务实现
 *
 * @author meishuquan
 * @date 2017/3/23
 */
@Service
public class CommonServiceImpl implements CommonService {

	@Autowired
	private MemberDOMapper memberDOMapper;

	@Autowired
	private MerchantDOMapper merchantDOMapper;

	@Autowired
	private MerchantStoreDOMapper merchantStoreDOMapper;
	@Autowired
	private InviteRelationDOMapper inviteRelationDOMapper;

	@Override
	public InviterBO getInviterByAccount(String account) {

		// 查询会员信息
		MemberDOExample memberDOExample = new MemberDOExample();
		memberDOExample.createCriteria().andAccountEqualTo(account);
		List<MemberDO> memberDOS = memberDOMapper.selectByExample(memberDOExample);
		if (!memberDOS.isEmpty()) {
			return InviterConverter.convertBO(memberDOS.get(0));
		}

		// 查询商户信息
		MerchantDOExample merchantDOExample = new MerchantDOExample();
		merchantDOExample.createCriteria().andAccountEqualTo(account);
		List<MerchantDO> merchantDOS = merchantDOMapper.selectByExample(merchantDOExample);
		if (!merchantDOS.isEmpty()) {
			MerchantStoreDOExample merchantStoreDOExample = new MerchantStoreDOExample();
			merchantStoreDOExample.createCriteria().andMerchantIdEqualTo(merchantDOS.get(0).getId());
			List<MerchantStoreDO> merchantStoreDOS = merchantStoreDOMapper.selectByExample(merchantStoreDOExample);
			if (!merchantStoreDOS.isEmpty()) {
				return InviterConverter.convertBO(merchantStoreDOS.get(0), merchantDOS.get(0));
			}
			return InviterConverter.convertBO(merchantDOS.get(0));
		}
		return null;
	}

	@Override
	public List<String> selectHigherLevelInviters(String invitedUserNum, int level) {
		InviteRelationDOExample example = new InviteRelationDOExample();
		example.createCriteria().andInviteUserNumEqualTo(invitedUserNum).andDepthBetween(0, level);
		example.setOrderByClause(" depth asc ");
		List<InviteRelationDO> dos = inviteRelationDOMapper.selectByExample(example);
		if(dos == null || dos.isEmpty()){
			return null;
		}
		List<String> list = new ArrayList<String>();
		for(InviteRelationDO irdo : dos){
			list.add(irdo.getUserNum());
		}
		return list;
	}
}
