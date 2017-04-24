package com.lawu.eshop.member.api.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.user.dto.FavoriteMerchantDTO;
import com.lawu.eshop.user.param.FavoriteMerchantParam;

/**
 * api 商家收藏接口
 * @author zhangrc
 *
 */
@FeignClient(value= "user-srv" )
public interface FavoriteMerchantService {
	
	 @RequestMapping(method = RequestMethod.POST, value = "favoriteMerchant/save")
	 Result save(@RequestParam("memberId")  Long memberId ,@RequestParam("merchantId") Long merchantId);
	 
	 @RequestMapping(method = RequestMethod.GET,value = "favoriteMerchant/getMyFavoriteMerchant")
	 Result<Page<FavoriteMerchantDTO>> getMyFavoriteMerchant(@RequestParam("memberId")  Long memberId ,@RequestBody FavoriteMerchantParam pageQuery);
	 
	 @RequestMapping(method = RequestMethod.DELETE, value = "favoriteMerchant/remove/{merchantId}")
	 Result remove(@PathVariable("merchantId")  Long merchantId ,@RequestParam("memberId")  Long memberId);
}
