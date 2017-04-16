package com.lawu.eshop.merchant.api.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.property.param.ThirdPayDataParam;
import com.lawu.eshop.property.param.PcAlipayDataParam;

/**
 * 
 * <p>
 * Description: 支付宝
 * </p>
 * @author Yangqh
 * @date 2017年4月7日 上午9:12:31
 *
 */
@FeignClient(value= "property-srv")
public interface AlipayService {
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(method = RequestMethod.POST, value = "alipay/getAppAlipayReqParams")
	Result getAppAlipayReqParams(@RequestBody ThirdPayDataParam param);

	@SuppressWarnings("rawtypes")
	@RequestMapping(method = RequestMethod.POST, value = "alipay/initPcPay")
	Result initPcPay(PcAlipayDataParam param);

}
