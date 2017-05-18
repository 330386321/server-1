package com.lawu.eshop.cache.srv.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.cache.srv.service.TransactionService;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;

/**
 * 分布式事务定时任务缓存接口
 * 
 * @author Sunny
 * @date 2017年5月18日
 */
@RestController
@RequestMapping(value = "transaction/")
public class TransactionController extends BaseController{
	
	@Autowired
	private TransactionService tansactionService;
	
	/**
	 * 获取次数
	 * 
	 * @param key 
	 * @return
	 * @author Sunny
	 */
	@RequestMapping(value = "count/{type}", method = RequestMethod.GET)
    public Result<Long> getCount(@PathVariable String type) {
		Long count = tansactionService.getCount(type);
		return successGet(count);
    }
	
	/**
	 * 添加次数
	 * 
	 * @param key
	 * @author Sunny
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "count/{type}", method = RequestMethod.PUT)
    public Result addCount(@PathVariable String type) {
		tansactionService.addCount(type);
		return successCreated();
    }
}
