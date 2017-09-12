package com.lawu.eshop.cache.srv.service;

/**
 * 
 * 广告可领取数记录
 * @author zhangrc
 * @date 2017/09/12
 *
 */
public interface AdCountService {
	
	/**
	 * 存入记录
	 * @param key
	 */
	void setAdCountRecord(Long id , Integer count);
	
	/**
	 * 获取数量
	 * @param id
	 * @return
	 */
	Integer getAdCountRecord(Long id);
	
	/**
	 * 修改数量
	 * @param key
	 * @return
	 */
	void updateAdCountRecord(Long id);

}
