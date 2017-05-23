package com.lawu.eshop.statistics.service;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.lawu.eshop.framework.web.Result;

@FeignClient(value = "cache-srv")
public interface AdViewService {
	
	@RequestMapping(value = "adView/getAdviews", method = RequestMethod.GET)
	Result<List<String>> getAdviews(@RequestParam("adId") String adId);

}
