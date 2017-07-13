package com.lawu.eshop.ad.srv.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawu.eshop.ad.constants.PointPoolStatusEnum;
import com.lawu.eshop.ad.constants.PointPoolTypeEnum;
import com.lawu.eshop.ad.srv.bo.GetRedPacketBO;
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
		long  count=pointPoolDOMapper.countByExample(example);
		return count==0?false:true;
	}

	@Override
	public GetRedPacketBO isGetRedPacket(Long merchantId, String userNum) {
		PointPoolDOExample example=new PointPoolDOExample();
		example.createCriteria().andMerchantIdEqualTo(merchantId).andStatusEqualTo(PointPoolStatusEnum.AD_POINT_NO_GET.val).andTypeEqualTo(PointPoolTypeEnum.AD_TYPE_PACKET.val);
		List<PointPoolDO> list=pointPoolDOMapper.selectByExample(example);
		GetRedPacketBO bo=new GetRedPacketBO();
		if(list.isEmpty()){
			 bo.setNullRedPacket(true);
			 return bo;
		}else{
			PointPoolDOExample example2=new PointPoolDOExample(); 
			example2.createCriteria().andAdIdEqualTo(list.get(0).getAdId()).andMemberNumEqualTo(userNum);
			Long count=pointPoolDOMapper.countByExample(example2);
			if(count.intValue()>0){
				bo.setGetRedPacket(true);
			}else{
				bo.setGetRedPacket(false);
			}
			return bo;
		}
		
	}

	


}
