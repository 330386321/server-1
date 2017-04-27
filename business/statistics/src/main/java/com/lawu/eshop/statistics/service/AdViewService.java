package com.lawu.eshop.statistics.service;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "cache-srv")
public interface AdViewService {
	
	@RequestMapping(value = "adView/getAdviews", method = RequestMethod.GET)
	List<String> getAdviews(@RequestParam("adId") String adId);

}
