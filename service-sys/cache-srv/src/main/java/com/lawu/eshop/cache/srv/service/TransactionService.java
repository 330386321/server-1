package com.lawu.eshop.cache.srv.service;

/**
 * 分布式事务定时任务缓存服务接口
 * 
 * @author Sunny
 * @date 2017年5月18日
 */
public interface TransactionService {
	
	/**
	 * 获取次数
	 * 
	 * @param key 
	 * @return
	 * @author Sunny
	 */
	Long getCount(String type);
	
	/**
	 * 添加次数
	 * 
	 * @param key
	 * @author Sunny
	 */
	void addCount(String type);
}
