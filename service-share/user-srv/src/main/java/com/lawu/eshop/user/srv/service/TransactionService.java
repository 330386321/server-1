package com.lawu.eshop.user.srv.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lawu.eshop.framework.web.Result;

/**
 * 
 * @author Sunny
 * @date 2017年5月18日
 */
@FeignClient(value = "cache-srv")
public interface TransactionService {

	/**
	 * 获取次数
	 * 
	 * @param key 
	 * @return
	 * @author Sunny
	 */
	@RequestMapping(value = "transaction/count/{type}", method = RequestMethod.GET)
    Result<Long> getCount(@PathVariable("type") String type);
	
	/**
	 * 添加次数
	 * 
	 * @param key
	 * @author Sunny
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "transaction/count/{type}", method = RequestMethod.PUT)
    Result addCount(@PathVariable("type") String type);
}
