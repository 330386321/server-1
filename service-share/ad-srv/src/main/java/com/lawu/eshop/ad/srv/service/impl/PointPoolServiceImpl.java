package com.lawu.eshop.ad.srv.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawu.eshop.ad.srv.mapper.extend.PointPoolDOMapperExtend;
import com.lawu.eshop.ad.srv.service.PointPoolService;

@Service
public class PointPoolServiceImpl implements PointPoolService {
	
	@Autowired 
	private PointPoolDOMapperExtend pointPoolDOMapperExtend;

	@Override
	public List<Long> selectMemberList(Long adId) {
		 List<Long>  memberIds=pointPoolDOMapperExtend.selectMember(adId);
		return memberIds;
	}

}
