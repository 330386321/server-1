package com.lawu.eshop.synchronization.lock.impl;

import java.util.concurrent.TimeUnit;

import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.lawu.eshop.synchronization.lock.constants.LockConstant;
import com.lawu.eshop.synchronization.lock.constants.LockConstant.LockModule;
import com.lawu.eshop.synchronization.lock.service.LockService;

/**
 * 分布式事务定时任务缓存服务接口实现类
 * 
 * @author Sunny
 * @date 2017年5月18日
 */
public class LockServiceImpl implements LockService {
	
	private static Logger logger = LoggerFactory.getLogger(LockServiceImpl.class);
	
	@Autowired
	private RedissonClient redissonClient;
	
	@Value("${redis.lock.waitTime}")
	private long waitTime;
	
	@Value("${redis.lock.leaseTime}")
	private long leaseTime;
	
	/**
	 * 判断锁是否存在
	 * 如果存在返回false
	 * 如果不存在，生成一个锁，并且返回true
	 * 
	 * @param lockKey 锁的名称
	 * @return
	 * @author Sunny
	 * @date 2017年5月31日
	 */
	@Override
	public boolean tryLock(String lockKey) {
		boolean rtn = false;
		RLock rLock = getLock(lockKey);
		try {
			rtn = rLock.tryLock(waitTime, leaseTime, TimeUnit.MILLISECONDS);
		} catch (InterruptedException e) {
			logger.error("获取锁失败", e);
		}
		return rtn;
	}
	
	/**
	 * 判断锁是否存在
	 * 如果存在返回false
	 * 如果不存在，生成一个锁，并且返回true
	 * 
	 * @param lockModule 锁的模块
	 * @param lockKey 锁的名称
	 * @param relatedId 关联id
	 * @return
	 * @author Sunny
	 * @date 2017年5月31日
	 */
	@Override
	public boolean tryLock(LockModule lockModule, String lockKey, Long relatedId) {
		// 拼接锁名
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(lockModule.getName()).append("_").append(lockKey).append("_").append(relatedId);
		return tryLock(stringBuilder.toString());
	}
	
	/**
	 * 判断锁是否存在
	 * 如果存在返回false
	 * 如果不存在，生成一个锁，并且返回true
	 * 
	 * @param lockModule 锁的模块
	 * @param lockKey 锁的名称
	 * @return
	 * @author Sunny
	 * @date 2017年5月31日
	 */
	@Override
	public boolean tryLock(LockModule lockModule, String lockKey) {
		// 拼接锁名
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(lockModule.getName()).append("_").append(lockKey);
		return tryLock(stringBuilder.toString());
	}

	/**
	 * 释放锁
	 * 
	 * @param lockKey 锁的名称
	 * @author Sunny
	 * @date 2017年5月31日
	 */
	@Override
	public void unLock(String lockKey) {
		RLock rLock = getLock(lockKey);
		try {
			rLock.unlock();
		} catch (IllegalMonitorStateException e) {
			logger.error("释放锁失败", e);
		}
	}
	
	/**
	 * 释放锁
	 * 
	 * @param lockModule 锁的模块
	 * @param lockKey 锁的名称
	 * @param relatedId 关联id
	 * @author Sunny
	 * @date 2017年5月31日
	 */
	@Override
	public void unLock(LockModule lockModule, String lockKey, Long relatedId) {
		// 拼接锁名
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(lockModule.getName()).append("_").append(lockKey).append("_").append(relatedId);
		unLock(stringBuilder.toString());
	}
	
	/**
	 * 释放锁
	 * 
	 * @param lockModule 锁的模块
	 * @param lockKey 锁的名称
	 * @author Sunny
	 * @date 2017年5月31日
	 */
	@Override
	public void unLock(LockModule lockModule, String lockKey) {
		// 拼接锁名
		StringBuilder stringBuilder = new StringBuilder();
		stringBuilder.append(lockModule.getName()).append("_").append(lockKey);
		unLock(stringBuilder.toString());
	}
	
	/**
	 * 获取锁
	 * 
	 * @param lockKey 锁的名称
	 * @return
	 * @author Sunny
	 * @date 2017年5月31日
	 */
	private RLock getLock(String lockKey){
		String key = LockConstant.DISTRIBUTED_SYNCHRONIZATION_LOCK.concat(lockKey);
		return redissonClient.getLock(key);
	}
}
