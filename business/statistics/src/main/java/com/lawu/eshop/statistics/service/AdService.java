package com.lawu.eshop.statistics.service;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.lawu.eshop.ad.dto.MemberAdRecodeCommissionDTO;
import com.lawu.eshop.ad.param.AdCommissionJobParam;

@FeignClient(value= "ad-srv")
public interface AdService {

	/**
	 * 获取未计算提成的会员点击广告记录
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "commission/getNoneCommissionAds")
	List<MemberAdRecodeCommissionDTO> getNoneCommissionAds();

	/**
	 * 计算上级提成
	 * @param param
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "commission/calculation")
	int calculation(@RequestBody AdCommissionJobParam param);

}
