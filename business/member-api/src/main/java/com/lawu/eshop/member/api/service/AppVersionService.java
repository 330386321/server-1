package com.lawu.eshop.member.api.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.mall.dto.AppVersionDTO;

@FeignClient(value = "mall-srv")
public interface AppVersionService {

	
	@RequestMapping(value = "appVersion/getAppVersion/{appType}", method = RequestMethod.GET)
    public Result<AppVersionDTO> getAppVersion(@PathVariable("appType") Integer appType);
	
}
