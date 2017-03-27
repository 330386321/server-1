package com.lawu.eshop.member.api.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.lawu.eshop.framework.web.Result;

/**
 * api 商家收藏接口
 * @author zhangrc
 *
 */
@FeignClient(value= "user-srv" )
public interface FavoriteMerchantService {
	
	 @RequestMapping(method = RequestMethod.POST, value = "favoriteMerchant/save")
	 Result save(@RequestParam("memberId")  Long memberId ,@RequestParam("merchantId") Long merchantId);
}
