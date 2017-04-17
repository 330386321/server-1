package com.lawu.eshop.member.api.service;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.lawu.eshop.framework.web.Result;

@FeignClient(value = "user-srv")
public interface FansMerchantService {
	
	@RequestMapping(value = "fansMerchant/isFansMerchant/{merchantId}", method = RequestMethod.GET)
    public Result<Boolean> isFansMerchant(@PathVariable("merchantId") Long merchantId, @RequestParam("memberId") Long memberId);
	
	@RequestMapping(value = "fansMerchant/findMerchant", method = RequestMethod.GET)
    public List<Long> findMerchant(@RequestParam("memberId") Long memberId);

}
