package com.lawu.eshop.cache.srv.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.lawu.eshop.cache.srv.constants.KeyConstant;
import com.lawu.eshop.cache.srv.service.TransactionService;

/**
 * 分布式事务定时任务缓存服务接口实现类
 * 
 * @author Sunny
 * @date 2017年5月18日
 */
@Service
public class TransactionServiceImpl implements TransactionService {
	
	@Autowired
    private StringRedisTemplate stringRedisTemplate;

	/**
	 * 获取次数
	 * 
	 * @param key 
	 * @return
	 * @author Sunny
	 */
	@Override
	public Long getCount(String type) {
		Long rtn = 0L;
		String key = KeyConstant.REDIS_KEY_TRANSACTION_EXECUTION_COUNT_PREFIX.concat(type);
		String value = stringRedisTemplate.opsForValue().get(key);
		if (!StringUtils.isEmpty(value)) {
			rtn = Long.valueOf(value);
		} else {
			// 如果key不存在,放入0
			stringRedisTemplate.opsForValue().set(key, rtn.toString());
		}
		return rtn;
	}

	/**
	 * 添加次数
	 * 
	 * @param key
	 * @author Sunny
	 */
	@Override
	public void addCount(String type) {
		String key = KeyConstant.REDIS_KEY_TRANSACTION_EXECUTION_COUNT_PREFIX.concat(type);
		// 次数加1
		stringRedisTemplate.opsForValue().increment(key, 1L);
	}
	
}
