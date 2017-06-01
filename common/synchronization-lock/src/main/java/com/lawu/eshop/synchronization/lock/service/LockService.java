package com.lawu.eshop.synchronization.lock.service;

/**
 * 分布式事务同步锁缓存服务接口
 * 
 * @author Sunny
 * @date 2017年5月18日
 */
public interface LockService {
	
	/**
	 * 判断锁是否存在
	 * 如果存在返回false
	 * 如果不存在，生成一个锁，并且返回true
	 * 
	 * @param lockName 锁的名称
	 * @return
	 * @author Sunny
	 * @date 2017年5月31日
	 */
	boolean tryLock(String lockName);
	
	/**
	 * 释放锁
	 * 
	 * @param lockName 锁的名称
	 * @author Sunny
	 * @date 2017年5月31日
	 */
	void unLock(String lockName);
}
