package com.lawu.eshop.operator.api.service;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.user.dto.MerchantStorePlatDTO;
import com.lawu.eshop.user.param.MerchantStorePlatParam;

@FeignClient(value = "user-srv")
public interface MerchantStoreService {
	
	 @RequestMapping(value = "merchantStore/selectAllMerchantStore",method = RequestMethod.POST)
	 Result<List<MerchantStorePlatDTO>> selectAllMerchantStore(@ModelAttribute MerchantStorePlatParam param);

}
