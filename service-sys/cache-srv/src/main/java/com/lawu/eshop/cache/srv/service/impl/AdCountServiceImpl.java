package com.lawu.eshop.cache.srv.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.core.RedisOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SessionCallback;
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
	public Object getAdCountRecord(Long id) {
		stringRedisTemplate.setEnableTransactionSupport(true);
		String key = KeyConstant.REDIS_KEY_AD_COUNT.concat(id.toString());
		stringRedisTemplate.watch(key);		
		stringRedisTemplate.multi();
		//总数减一
		stringRedisTemplate.boundValueOps(key).increment(-1);
		Object rs = stringRedisTemplate.exec();
		return rs;
	}
	


}
