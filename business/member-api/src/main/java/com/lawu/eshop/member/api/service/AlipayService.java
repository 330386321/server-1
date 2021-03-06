package com.lawu.eshop.member.api.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.property.param.ThirdPayDataParam;
import com.lawu.eshop.property.param.PcAlipayParam;

/**
 * 
 * <p>
 * Description: 前端用户提现
 * </p>
 * @author Yangqh
 * @date 2017年4月5日 下午6:18:44
 *
 */
@FeignClient(value= "property-srv")
public interface AlipayService {
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(method = RequestMethod.POST, value = "alipay/getAppAlipayReqParams")
	Result getAppAlipayReqParams(@RequestBody ThirdPayDataParam param);

	@SuppressWarnings("rawtypes")
	@RequestMapping(method = RequestMethod.POST, value = "alipay/initPcPay")
	Result initPcPay(PcAlipayParam param);

}
