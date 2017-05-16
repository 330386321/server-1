package com.lawu.eshop.ad.srv.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawu.eshop.ad.srv.domain.PointPoolDO;
import com.lawu.eshop.ad.srv.domain.PointPoolDOExample;
import com.lawu.eshop.ad.srv.mapper.PointPoolDOMapper;
import com.lawu.eshop.ad.srv.mapper.extend.PointPoolDOMapperExtend;
import com.lawu.eshop.ad.srv.service.PointPoolService;

@Service
public class PointPoolServiceImpl implements PointPoolService {
	
	@Autowired 
	private PointPoolDOMapperExtend pointPoolDOMapperExtend;
	
	@Autowired
	private PointPoolDOMapper pointPoolDOMapper;

	@Override
	public List<PointPoolDO> selectMemberList(Long adId) {
		 List<PointPoolDO>  pointPoolDO=pointPoolDOMapperExtend.selectMember(adId);
		return pointPoolDO;
	}

	@Override
	public Boolean selectStatusByMember(Long adId,Long memberId) {
		PointPoolDOExample example=new PointPoolDOExample();
		example.createCriteria().andMemberIdEqualTo(memberId).andTypeEqualTo(new Byte("1")).andAdIdEqualTo(adId);
		List<PointPoolDO> list=pointPoolDOMapper.selectByExample(example);
		if(list.isEmpty())
			return false;
		else
			return true;
	}

	@Override
	public Boolean isGetRedPacket(Long merchantId, String userNum) {
		PointPoolDOExample example=new PointPoolDOExample();
		example.createCriteria().andMerchantIdEqualTo(merchantId).andStatusEqualTo(new Byte("0")).andTypeEqualTo(new Byte("2"));
		List<PointPoolDO> list=pointPoolDOMapper.selectByExample(example);
		Boolean flag=true;
		if(!list.isEmpty()){
			PointPoolDOExample example2=new PointPoolDOExample();
			example2.createCriteria().andAdIdEqualTo(list.get(0).getAdId()).andMemberNumEqualTo(userNum);
			List<PointPoolDO> isGetRedPacket=pointPoolDOMapper.selectByExample(example2);
			if(isGetRedPacket.isEmpty()){
				flag= false;
			}else{
				flag= true;
			}
			
		}
		return flag;
			
	}

	


}
