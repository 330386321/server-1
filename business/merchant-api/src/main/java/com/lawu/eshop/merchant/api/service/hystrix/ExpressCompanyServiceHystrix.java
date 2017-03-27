package com.lawu.eshop.merchant.api.service.hystrix;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.mall.dto.ExpressCompanyDTO;
import com.lawu.eshop.merchant.api.service.ExpressCompanyService;

/**
 * 
 * @author Sunny
 * @date 2017/3/26
 */
@Component
public class ExpressCompanyServiceHystrix implements ExpressCompanyService {
	
	private static Logger logger = LoggerFactory.getLogger(ExpressCompanyServiceHystrix.class);

	@Override
	public Result<List<ExpressCompanyDTO>> list() {
		logger.warn("ExpressCompanyService list method调用失败");
		return null;
	}

}
