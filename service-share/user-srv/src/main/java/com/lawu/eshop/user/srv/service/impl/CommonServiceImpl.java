package com.lawu.eshop.user.srv.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawu.eshop.user.constants.UserCommonConstant;
import com.lawu.eshop.user.dto.CommissionInvitersUserDTO;
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
	public List<CommissionInvitersUserDTO> selectHigherLevelInviters(String invitedUserNum, int level,
			boolean isLevel) {
		InviteRelationDOExample example = new InviteRelationDOExample();
		example.createCriteria().andInviteUserNumEqualTo(invitedUserNum).andDepthBetween(1, level);
		example.setOrderByClause(" depth asc ");
		List<InviteRelationDO> dos = inviteRelationDOMapper.selectByExample(example);
		if (dos == null || dos.isEmpty()) {
			return null;
		}
		List<CommissionInvitersUserDTO> list = new ArrayList<CommissionInvitersUserDTO>();
		for (InviteRelationDO irdo : dos) {
			CommissionInvitersUserDTO user = new CommissionInvitersUserDTO();
			user.setLevel(1);
			if(isLevel){
				if (irdo.getUserNum().startsWith(UserCommonConstant.MEMBER_NUM_TAG)) {
					MemberDOExample memberExample = new MemberDOExample();
					memberExample.createCriteria().andNumEqualTo(irdo.getUserNum());
					List<MemberDO> members = memberDOMapper.selectByExample(memberExample);
					if(members != null && !members.isEmpty() && members.size() > 0){
						user.setLevel(members.get(0).getLevel());
					}
				} else if (irdo.getUserNum().startsWith(UserCommonConstant.MERCHANT_NUM_TAG)) {
					// 商家目前不按等级计算提成
				}
			}
			user.setUserNum(irdo.getUserNum());

			list.add(user);
		}
		return list;
	}
}
