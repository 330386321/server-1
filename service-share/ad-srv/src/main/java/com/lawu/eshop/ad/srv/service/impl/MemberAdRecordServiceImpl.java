package com.lawu.eshop.ad.srv.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.lawu.eshop.ad.constants.MemberAdRecordStatusEnum;
import com.lawu.eshop.ad.srv.bo.MemberAdRecodeCommissionBO;
import com.lawu.eshop.ad.srv.domain.MemberAdRecordDO;
import com.lawu.eshop.ad.srv.domain.MemberAdRecordDOExample;
import com.lawu.eshop.ad.srv.mapper.MemberAdRecordDOMapper;
import com.lawu.eshop.ad.srv.service.MemberAdRecordService;
import com.lawu.eshop.framework.web.ResultCode;

@Service
public class MemberAdRecordServiceImpl implements MemberAdRecordService {

	@Autowired
	private MemberAdRecordDOMapper memberAdRecordDOMapper;

	@Override
	public boolean isClickToDay(Long memberId, Long adId) {
		MemberAdRecordDOExample example = new MemberAdRecordDOExample();
		example.createCriteria().andAdIdEqualTo(adId).andMemberIdEqualTo(memberId).andClickDateEqualTo(new Date());
		long count = memberAdRecordDOMapper.countByExample(example);
		return count > 0?true:false;

	}

	@Override
	public List<MemberAdRecodeCommissionBO> getNoneCommissionAds() {
		MemberAdRecordDOExample example = new MemberAdRecordDOExample();
		example.createCriteria().andStatusEqualTo(MemberAdRecordStatusEnum.NONE.getVal());
		List<MemberAdRecordDO> dos = memberAdRecordDOMapper.selectByExample(example);
		List<MemberAdRecodeCommissionBO> bos = new ArrayList<MemberAdRecodeCommissionBO>();
		for(MemberAdRecordDO mdo : dos){
			MemberAdRecodeCommissionBO bo = new MemberAdRecodeCommissionBO();
			bo.setId(mdo.getId());
			bo.setAdId(mdo.getAdId());
			bo.setMemberNum(mdo.getMemberNum());
			bo.setStatus(mdo.getStatus());
			bo.setPoint(mdo.getOriginalPoint());
			bos.add(bo);
		}
		return bos;
	}

	@Override
	@Transactional
	public int updateMemberAdRecardStatus(Long id) {
		MemberAdRecordDOExample example = new MemberAdRecordDOExample();
		example.createCriteria().andIdEqualTo(id);
		MemberAdRecordDO mdo = new MemberAdRecordDO();
		mdo.setStatus(MemberAdRecordStatusEnum.YES.getVal());
		mdo.setGmtCommission(new Date());
		memberAdRecordDOMapper.updateByExampleSelective(mdo, example);
		return ResultCode.SUCCESS;
	}

}
