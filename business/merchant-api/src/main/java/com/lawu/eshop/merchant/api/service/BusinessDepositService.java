package com.lawu.eshop.merchant.api.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.property.dto.BusinessDepositInitDTO;
import com.lawu.eshop.property.param.BusinessDepositSaveDataParam;

/**
 * 
 * <p>
 * Description: 商家保证金
 * </p>
 * @author Yangqh
 * @date 2017年4月15日 上午11:04:43
 *
 */
@FeignClient(value= "property-srv")
public interface BusinessDepositService {

	/**
	 * 初始化商家保证金记录
	 * @param param
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "businessDeposit/save")
	Result<BusinessDepositInitDTO> save(@RequestBody BusinessDepositSaveDataParam param);

	

}
