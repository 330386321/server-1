package com.lawu.eshop.ad.srv.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawu.eshop.ad.constants.AdStatusEnum;
import com.lawu.eshop.ad.constants.AdTypeEnum;
import com.lawu.eshop.ad.srv.domain.AdDO;
import com.lawu.eshop.ad.srv.domain.AdDOExample;
import com.lawu.eshop.ad.srv.domain.PointPoolDO;
import com.lawu.eshop.ad.srv.domain.PointPoolDOExample;
import com.lawu.eshop.ad.srv.mapper.AdDOMapper;
import com.lawu.eshop.ad.srv.mapper.PointPoolDOMapper;
import com.lawu.eshop.ad.srv.mapper.extend.PointPoolDOMapperExtend;
import com.lawu.eshop.ad.srv.service.PointPoolService;

@Service
public class PointPoolServiceImpl implements PointPoolService {
	
	@Autowired 
	private PointPoolDOMapperExtend pointPoolDOMapperExtend;
	
	@Autowired
	private PointPoolDOMapper pointPoolDOMapper;

	@Autowired
	private AdDOMapper adDOMapper;

	@Override
	public List<PointPoolDO> selectMemberList(Long adId) {
		 List<PointPoolDO>  pointPoolDO=pointPoolDOMapperExtend.selectMember(adId);
		return pointPoolDO;
	}

	@Override
	public Boolean selectStatusByMember(Long adId,Long memberId) {
		PointPoolDOExample example=new PointPoolDOExample();
		example.createCriteria().andMemberIdEqualTo(memberId).andTypeEqualTo(new Byte("1")).andAdIdEqualTo(adId);
		long  count=pointPoolDOMapper.countByExample(example);
		return count==0?false:true;
	}

	@Override
	public Boolean isGetRedPacket(Long merchantId, String userNum) {
		AdDOExample adDOExample = new AdDOExample();
		adDOExample.createCriteria().andMerchantIdEqualTo(merchantId).andTypeEqualTo(AdTypeEnum.AD_TYPE_PACKET.val).andStatusEqualTo(AdStatusEnum.AD_STATUS_ADD.val);
		List<AdDO> list = adDOMapper.selectByExample(adDOExample);
		Long count=0l;
		if(!list.isEmpty()){
			PointPoolDOExample example=new PointPoolDOExample();
			example.createCriteria().andAdIdEqualTo(list.get(0).getId()).andMemberNumEqualTo(userNum);
			count=pointPoolDOMapper.countByExample(example);
		}
		return count.intValue()>0 ? true:false;

	}

	


}
