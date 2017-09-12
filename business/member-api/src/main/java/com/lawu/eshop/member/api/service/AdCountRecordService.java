package com.lawu.eshop.member.api.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.lawu.eshop.framework.web.Result;

@FeignClient(value = "cache-srv")
public interface AdCountRecordService {
	
	@RequestMapping(value = "adCount/getAdCountRecord", method = RequestMethod.GET)
    public Result<Integer> getAdCountRecord(@RequestParam("id") Long id);
	
	
	@RequestMapping(value = "adCount/updateAdCountRecord", method = RequestMethod.GET)
	Result updateAdCountRecord(@RequestParam("id") Long  id);

}
