package com.lawu.eshop.merchant.api.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.user.param.FansInviteContentParam;

/**
 * 邀请粉丝内容表接口
 * 
 * @author hongqm
 * @date 2017/08/04
 *
 */
@FeignClient(value = "user-srv")
public interface FansInviteContentService {

	
	@SuppressWarnings("rawtypes")
	@RequestMapping(method = RequestMethod.POST, value = "fansInviteContent/saveInviteContentService")
	Result saveFansInviteContent(@RequestBody FansInviteContentParam address);
	
}
