package com.lawu.eshop.member.api.service;

import javax.validation.Valid;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.property.param.PropertyInfoDataParam;

@FeignClient(value = "property-srv")
public interface PropertyInfoDataService {
	
	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "propertyInfoData/doHanlderMinusPoint", method = RequestMethod.POST)
	public Result doHanlderMinusPoint(@RequestBody @Valid PropertyInfoDataParam param);

}
