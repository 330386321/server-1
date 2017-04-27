package com.lawu.eshop.cache.srv.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ListOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.lawu.eshop.cache.srv.service.AdViewService;

@Service
public class AdViewServiceImpl implements AdViewService {
	
	@Autowired
    private StringRedisTemplate stringRedisTemplate;

	@Override
	public void setAdView(String adId, String memberId) {
		 ListOperations<String, String> listOperation =stringRedisTemplate.opsForList();
		 listOperation.leftPush(adId, memberId);
		 
		 
	}

	@Override
	public List<String> getAdviews(String adId) {
		List<String> list=new ArrayList<>();
		ListOperations<String, String> listOperation =stringRedisTemplate.opsForList();
		Long size = listOperation.size(adId);
		for (int i = 0; i < size; i++) { 
			list.add(listOperation.index(adId,i));
	    }
		return list;
	}
	
	
	
}
