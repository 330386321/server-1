package com.lawu.eshop.cache.srv.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.lawu.eshop.cache.srv.constants.KeyConstant;
import com.lawu.eshop.cache.srv.service.AdCountService;

@Service
public class AdCountServiceImpl implements AdCountService {
	
	@Autowired
    private StringRedisTemplate stringRedisTemplate;

	@Override
	public void setAdCountRecord(Long id,Integer count) {
		 String key = KeyConstant.REDIS_KEY_AD_COUNT.concat(id.toString());
		 stringRedisTemplate.opsForValue().set(key, count.toString());
	}

	@Override
	public Integer getAdCountRecord(Long id) {
		String key = KeyConstant.REDIS_KEY_AD_COUNT.concat(id.toString());
		String value = stringRedisTemplate.opsForValue().get(key);
		return Integer.parseInt(value);
	}

	@Override
	public void updateAdCountRecord(Long id) {
		String key = KeyConstant.REDIS_KEY_AD_COUNT.concat(id.toString());
		// 总数减一
		stringRedisTemplate.boundValueOps(key).increment(-1);
	}

}
