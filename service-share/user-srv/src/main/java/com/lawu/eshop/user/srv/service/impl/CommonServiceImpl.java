package com.lawu.eshop.user.srv.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.user.constants.UserTypeEnum;
import com.lawu.eshop.user.dto.EFriendInviterDTO;
import com.lawu.eshop.user.dto.MerchantStatusEnum;
import com.lawu.eshop.user.param.EFriendQueryDataParam;
import com.lawu.eshop.user.srv.UserSrvConfig;
import com.lawu.eshop.user.srv.bo.EFriendInviterBO;
import com.lawu.eshop.user.srv.domain.*;
import com.lawu.eshop.user.srv.domain.extend.InviteMerchantInfoView;
import com.lawu.eshop.user.srv.mapper.*;
import com.lawu.eshop.user.srv.mapper.extend.MemberDOMapperExtend;
import com.lawu.eshop.user.srv.mapper.extend.MerchantDOMapperExtend;
import org.apache.ibatis.session.RowBounds;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawu.eshop.user.constants.UserCommonConstant;
import com.lawu.eshop.user.dto.CommissionInvitersUserDTO;
import com.lawu.eshop.user.srv.bo.InviterBO;
import com.lawu.eshop.user.srv.converter.InviterConverter;
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

	@Autowired
	private MemberDOMapperExtend memberDOMapperExtend;

	@Autowired
	private MerchantDOMapperExtend merchantDOMapperExtend;

	@Autowired
	private UserSrvConfig userSrvConfig;

	@Autowired
	private MemberProfileDOMapper memberProfileDOMapper;

	@Autowired
	private MerchantProfileDOMapper merchantProfileDOMapper;

	@Autowired
	private MerchantStoreImageDOMapper merchantStoreImageDOMapper;

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
					if(members != null && !members.isEmpty()){
						user.setLevel(members.get(0).getLevel());
					}
				} else if (irdo.getUserNum().startsWith(UserCommonConstant.MERCHANT_NUM_TAG)) {
					MerchantDOExample merchantExample = new MerchantDOExample();
					merchantExample.createCriteria().andNumEqualTo(irdo.getUserNum());
					List<MerchantDO> merchants = merchantDOMapper.selectByExample(merchantExample);
					if(merchants != null && !merchants.isEmpty()){
						user.setLevel(merchants.get(0).getLevel());
					}
				}
			}
			user.setUserNum(irdo.getUserNum());
			list.add(user);
		}
		return list;
	}

	@Override
	public Page<EFriendInviterBO> selectEFriend(EFriendQueryDataParam dataParam) {
		List<String> userNumList = new ArrayList<>();
		if(dataParam.getQueryContent() != null && !"".equals(dataParam.getQueryContent())){
			//用户：账号或昵称、商家：账号或店铺名称
			List<String> memberNumList = memberDOMapperExtend.selectNumLikeContent("%" + dataParam.getQueryContent() + "%");
			List<String> merchantNumList = merchantDOMapperExtend.selectNumLikeContent("%" + dataParam.getQueryContent() + "%");
			userNumList.addAll(memberNumList);
			userNumList.addAll(merchantNumList);
		}

		RowBounds rowBounds = new RowBounds(dataParam.getOffset(), dataParam.getPageSize());
		InviteRelationDOExample example = new InviteRelationDOExample();
		com.lawu.eshop.user.srv.domain.InviteRelationDOExample.Criteria criteria = example.createCriteria();
		criteria.andUserNumEqualTo(dataParam.getUserNum()).andDepthEqualTo(1);
		if(!userNumList.isEmpty()){
			criteria.andInviteUserNumIn(userNumList);
		}
		List<InviteRelationDO> inviteUserList = inviteRelationDOMapper.selectByExampleWithRowbounds(example,rowBounds);
		Integer totalCount = inviteRelationDOMapper.countByExample(example);

		List<EFriendInviterBO> rtnList = new ArrayList<>();
		MemberDOExample memberDOExample = new MemberDOExample();
		for(InviteRelationDO invite : inviteUserList){
			EFriendInviterBO bo = new EFriendInviterBO();
			if(invite.getInviteUserNum().startsWith(UserCommonConstant.MEMBER_NUM_TAG)){
				memberDOExample.clear();
				memberDOExample.createCriteria().andNumEqualTo(invite.getInviteUserNum());
				List<MemberDO> memberList = memberDOMapper.selectByExample(memberDOExample);
				if(memberList == null || memberList.isEmpty()) {
					continue;
				}
				MemberDO member = memberList.get(0);
				bo.setTitleName("E店用户");
				bo.setGmtCreate(member.getGmtCreate());
				if(member.getHeadimg() != null && !"".equals(member.getHeadimg())){
					bo.setHeadImg(member.getHeadimg());
				}else{
					bo.setHeadImg(userSrvConfig.getDefaultHeadimg());
				}
				bo.setLevel(member.getLevel() == null ? 1 : member.getLevel());
				bo.setName(member.getNickname() == null ? "" : member.getNickname());
				bo.setAccount(member.getAccount().replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2"));
				bo.setRegionAddress(member.getRegionName() == null ? "" : member.getRegionName());
				MemberProfileDO memberProfileDO = memberProfileDOMapper.selectByPrimaryKey(member.getId());
				if(memberProfileDO == null){
					bo.setInviterCount(0);
				}else{
					bo.setInviterCount(memberProfileDO.getInviteMemberCount() + memberProfileDO.getInviteMemberCount2() + memberProfileDO.getInviteMerchantCount() + memberProfileDO.getInviteMerchantCount2());
				}
				bo.setTotalInviteCount(totalCount + bo.getInviterCount());

			}else if(invite.getInviteUserNum().startsWith(UserCommonConstant.MERCHANT_NUM_TAG)){
				List<InviteMerchantInfoView> merchantList = merchantDOMapperExtend.selectInviteMerchantInfo(invite.getInviteUserNum());
				if(merchantList == null || merchantList.isEmpty()) {
					continue;
				}
				InviteMerchantInfoView merchant = merchantList.get(0);
				if(merchant.getStoreName() != null && !"".equals(merchant.getStoreName())){
					bo.setTitleName(merchant.getStoreName());
				}else{
					bo.setTitleName("E店商家");
				}
				bo.setGmtCreate(merchant.getGmtCreate());
				bo.setLevel(merchant.getLevel() == null ? 1 : merchant.getLevel());
				bo.setName(merchant.getPrincipalName() == null ? "" : merchant.getPrincipalName());
				bo.setAccount(merchant.getAccount().replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2"));
				String detailAddress = merchant.getRegionName() == null ? "" : merchant.getRegionName() + merchant.getAddress() == null ? "" : merchant.getAddress();
				bo.setRegionAddress(detailAddress);
				bo.setMerchantStatus(MerchantStatusEnum.getEnum(merchant.getStatus()));

				MerchantProfileDO merchantProfileDO = merchantProfileDOMapper.selectByPrimaryKey(merchant.getId());
				if(merchantProfileDO == null){
					bo.setInviterCount(0);
				}else{
					bo.setInviterCount(merchantProfileDO.getInviteMemberCount() + merchantProfileDO.getInviteMemberCount2() + merchantProfileDO.getInviteMerchantCount() + merchantProfileDO.getInviteMerchantCount2());
				}
				bo.setTotalInviteCount(totalCount + bo.getInviterCount());

				MerchantStoreImageDOExample merchantStoreImageDOExample = new MerchantStoreImageDOExample();
				merchantStoreImageDOExample.createCriteria().andMerchantIdEqualTo(merchant.getId()).andStatusEqualTo(true).andTypeEqualTo(new Byte("3"));
				List<MerchantStoreImageDO>  merchantStoreImgList = merchantStoreImageDOMapper.selectByExample(merchantStoreImageDOExample);
				if(merchantStoreImgList == null || merchantStoreImgList.isEmpty()){
					bo.setHeadImg(userSrvConfig.getMerchant_headimg());
				}else{
					bo.setHeadImg(merchantStoreImgList.get(0).getPath());
				}
			}
			rtnList.add(bo);
		}
		Page<EFriendInviterBO> page = new Page<>();
		page.setTotalCount(totalCount);
		page.setCurrentPage(dataParam.getCurrentPage());
		page.setRecords(rtnList);
		return page;
	}
}
