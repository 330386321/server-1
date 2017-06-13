package com.lawu.eshop.cache.srv.service.impl;

import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.lawu.eshop.cache.srv.constants.KeyConstant;
import com.lawu.eshop.cache.srv.service.AdViewService;

@Service
public class AdViewServiceImpl implements AdViewService {
	
	@Autowired
    private StringRedisTemplate stringRedisTemplate;

	@Override
	public void setAdView(String adId, String memberId) {
		 String key = KeyConstant.REDIS_KEY_AD.concat(adId);
		 stringRedisTemplate.opsForSet().add(key, memberId);
	}

	@Override
	public Set<String> getAdviews(String adId) {
		String key = KeyConstant.REDIS_KEY_AD.concat(adId);
		return stringRedisTemplate.opsForSet().members(key);
	}
	
	
	
}
