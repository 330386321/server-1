package com.lawu.eshop.merchant.api.service.hystrix;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestBody;

import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.mall.param.SuggestionParam;
import com.lawu.eshop.merchant.api.service.SuggestionService;

/**
 * 
 * @author Sunny
 * @date 2017/3/26
 */
@Component
public class SuggestionServiceHystrix implements SuggestionService {
	
	private static Logger logger = LoggerFactory.getLogger(SuggestionServiceHystrix.class);
	
	@Override
	public Result<Integer> save(@RequestBody SuggestionParam parm) {
		logger.warn("SuggestionService save method调用失败");
		return null;
	}

}
