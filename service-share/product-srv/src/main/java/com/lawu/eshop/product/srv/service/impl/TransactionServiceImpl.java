package com.lawu.eshop.product.srv.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lawu.eshop.compensating.transaction.service.CacheService;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.product.srv.service.TransactionService;

/**
 * 
 * @author Sunny
 * @date 2017年5月18日
 */
@Service
public class TransactionServiceImpl implements CacheService {
	
	@Autowired
	private TransactionService transactionService; 
	
	/**
	 * 获取次数
	 * 
	 * @param key
	 * @return
	 * @author Sunny
	 */
    public Long getCount(String type){
    	Result<Long> result = transactionService.getCount(type);
    	return result.getModel();
    }
	
	/**
	 * 添加次数
	 * 
	 * @param key
	 * @author Sunny
	 */
    public void addCount(String type){
    	transactionService.addCount(type);
	}
}
