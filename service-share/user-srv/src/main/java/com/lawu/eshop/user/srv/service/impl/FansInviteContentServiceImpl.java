package com.lawu.eshop.user.srv.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lawu.eshop.compensating.transaction.TransactionMainService;
import com.lawu.eshop.user.param.FansInviteContentExtendParam;
import com.lawu.eshop.user.param.UserFansParam;
import com.lawu.eshop.user.srv.bo.FansInviteContentBO;
import com.lawu.eshop.user.srv.converter.FansInviteContentConverter;
import com.lawu.eshop.user.srv.domain.FansInviteContentDO;
import com.lawu.eshop.user.srv.domain.FansMerchantDO;
import com.lawu.eshop.user.srv.domain.MemberDO;
import com.lawu.eshop.user.srv.domain.MemberDOExample;
import com.lawu.eshop.user.srv.domain.extend.FansInviteContentDOView;
import com.lawu.eshop.user.srv.mapper.FansInviteContentDOMapper;
import com.lawu.eshop.user.srv.mapper.FansMerchantDOMapper;
import com.lawu.eshop.user.srv.mapper.MemberDOMapper;
import com.lawu.eshop.user.srv.mapper.extend.FansInviteContentDOMapperExtend;
import com.lawu.eshop.user.srv.mapper.extend.FansMerchantDOMapperExtend;
import com.lawu.eshop.user.srv.service.FansInviteContentService;

@Service
public class FansInviteContentServiceImpl implements FansInviteContentService{

	
	@Autowired
	private FansInviteContentDOMapper fansInviteContentDOMapper;
	
	@Autowired
	private FansMerchantDOMapper fansMerchantDOMapper;
	
	@Autowired
	private MemberDOMapper memberDOMapper;

	@Autowired
	private FansInviteContentDOMapperExtend fansInviteContentDOMapperExtend;

	@Autowired
	private FansMerchantDOMapperExtend fansMerchantDOMapperExtend;

	@Autowired
	@Qualifier("merchantFansTransactionMainServiceImpl")
	private TransactionMainService transactionMainService;
	
	@Override
	public FansInviteContentBO selectInviteContentById(Long id) {
		FansInviteContentDO ficDo = fansInviteContentDOMapper.selectByPrimaryKey(id);
		FansInviteContentBO result = FansInviteContentConverter.converterDoToBo(ficDo);
		return result;
	}

	@Override
	@Transactional
	public void dealOverdueFansInvite() {
		UserFansParam param = new UserFansParam();
		param.setGmtCreate(new Date());
		List<FansInviteContentDOView> viewList = fansInviteContentDOMapperExtend.listOverdueFansInvite(param);
		if (viewList == null || viewList.isEmpty()) {
			return;
		}
		FansInviteContentDO inviteContentDO = new FansInviteContentDO();
		inviteContentDO.setIsOverdue(true);
		inviteContentDO.setGmtModified(new Date());
		for (FansInviteContentDOView view : viewList) {
			param.setMerchantId(view.getMerchantId());
			int count = fansMerchantDOMapperExtend.countOverdueFans(param);
			fansMerchantDOMapperExtend.delOverdueFans(param);

			inviteContentDO.setId(view.getId());
			inviteContentDO.setRefuseNumber(count);
			fansInviteContentDOMapper.updateByPrimaryKeySelective(inviteContentDO);

			if (count > 0) {
				transactionMainService.sendNotice(view.getId());
			}
		}
	}


	@Transactional
	@Override
	public Long saveInviteContentService(FansInviteContentExtendParam inviteContentParam) {
		String[] num = inviteContentParam.getNums().split(",");
		Date date = new Date();
		for(String s : num) {
			MemberDOExample memberDOExample = new MemberDOExample();
			memberDOExample.createCriteria().andNumEqualTo(s);
			List<MemberDO> memberDO = memberDOMapper.selectByExample(memberDOExample);
			FansMerchantDO fansMerchantDO = new FansMerchantDO();
			fansMerchantDO.setMemberId(memberDO.get(0).getId());
			fansMerchantDO.setMerchantId(inviteContentParam.getMerchantId());
			fansMerchantDO.setChannel((byte)2);
			fansMerchantDO.setGmtCreate(date);
			fansMerchantDO.setStatus((byte)0);
			fansMerchantDOMapper.insert(fansMerchantDO);
		}
		FansInviteContentDO fansInviteContentDO = FansInviteContentConverter.converterFansInviteContentParam(inviteContentParam);
		fansInviteContentDO.setGmtCreate(date);
		fansInviteContentDO.setGmtModified(date);
		fansInviteContentDOMapper.insertSelective(fansInviteContentDO);
		return fansInviteContentDO.getId();
	}
	
	@Transactional
	@Override
	public Long saveInviteContentExtendService(FansInviteContentExtendParam inviteContentParam) {
		String[] id = inviteContentParam.getIds().split(",");
		Date date = new Date();
		for(int i = 0; i < id.length; i++) {
			FansMerchantDO fansMerchantDO = new FansMerchantDO();
			fansMerchantDO.setMemberId(Long.valueOf(id[i].toString()));
			fansMerchantDO.setMerchantId(inviteContentParam.getMerchantId());
			fansMerchantDO.setChannel((byte)2);
			fansMerchantDO.setGmtCreate(date);
			fansMerchantDO.setStatus((byte)0);
			fansMerchantDOMapper.insert(fansMerchantDO);
		}
		FansInviteContentDO fansInviteContentDO = FansInviteContentConverter.converterFansInviteContentParam(inviteContentParam);
		fansInviteContentDO.setGmtCreate(date);
		fansInviteContentDO.setGmtModified(date);
		fansInviteContentDOMapper.insertSelective(fansInviteContentDO);
		return fansInviteContentDO.getId();
	}
}
