package com.lawu.eshop.external.api.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.property.param.NotifyCallBackParam;

/**
 * 
 * <p>
 * Description: 充值余额或积分
 * </p>
 * @author Yangqh
 * @date 2017年4月13日 上午10:13:53
 *
 */
@FeignClient(value= "property-srv")
public interface RechargeService {

	/**
	 * 
	 * @param param
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	@RequestMapping(method = RequestMethod.POST, value = "recharge/doHandleRechargeNotify")
	Result doHandleRechargeNotify(@RequestBody NotifyCallBackParam param);

   
}
