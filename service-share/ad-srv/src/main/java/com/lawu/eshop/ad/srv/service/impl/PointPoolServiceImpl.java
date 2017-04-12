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
	public List<Long> selectMemberList(Long adId) {
		 List<Long>  memberIds=pointPoolDOMapperExtend.selectMember(adId);
		return memberIds;
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

}
