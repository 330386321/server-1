package com.lawu.eshop.cache.srv.service.impl;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import com.lawu.eshop.cache.srv.service.ClickAdRecordService;

@Service
public class ClickAdRecordServiceImpl implements ClickAdRecordService{
	
	@Autowired
    private StringRedisTemplate stringRedisTemplate;

	@Override
	public void setClickAdRecord(String key) {
	    stringRedisTemplate.opsForValue().set(key, "true",1,TimeUnit.DAYS);
	}

	@Override
	public boolean getClickAdRecord(String key) {
		 return stringRedisTemplate.hasKey(key);
	}
	
	

}
