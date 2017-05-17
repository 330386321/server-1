package com.lawu.eshop.merchant.api.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.lawu.eshop.ad.dto.AdDTO;
import com.lawu.eshop.ad.dto.AdMerchantDTO;
import com.lawu.eshop.ad.dto.AdMerchantDetailDTO;
import com.lawu.eshop.ad.param.AdMerchantParam;
import com.lawu.eshop.ad.param.AdSaveParam;
import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.Result;

@FeignClient(value = "ad-srv")
public interface AdService {
	
	/**
	 * 添加E赚
	 * @param adParam
	 * @param merchantId
	 * @param mediaUrl
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "ad/saveAd")
	Result saveAd(@RequestBody AdSaveParam adSaveParam);
	
	/**
	 * 查询广告
	 * @param adMerchantParam
	 * @param memberId
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "ad/selectListByMerchant")
    Result<Page<AdMerchantDTO>> selectListByMerchant(@RequestBody AdMerchantParam adMerchantParam,@RequestParam("memberId") Long memberId);
	
	/**
	 * 操作广告下架
	 * @param statusEnum
	 * @param id
	 * @return
	 */
	@RequestMapping(method = RequestMethod.PUT,value = "ad/updateStatus/{id}")
    public Result updateStatus(@PathVariable("id") Long id);
	
	/**
	 * 操作广告删除
	 * @param statusEnum
	 * @param id
	 * @return
	 */
	@RequestMapping(method = RequestMethod.PUT,value = "ad/remove/{id}")
    public Result remove(@PathVariable("id") Long id);
	
	 /**
	  * 单个查询广告
	  * @return
	  */
	@RequestMapping(method = RequestMethod.GET, value = "ad/selectAbById/{id}")
	Result<AdDTO> selectAbById(@PathVariable("id") Long id);
	
	/**
	 * 商家端详情
	 * @param id
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "ad/selectById/{id}")
	Result<AdMerchantDetailDTO> selectById(@PathVariable("id") Long id);

	/**
	 * 判断红包是否领取完
	 * @param merchantId
	 * @return
	 */
	@RequestMapping(value = "ad/isExistsRedPacket/{merchantId}", method = RequestMethod.GET)
	Result<Boolean> isExistsRedPacket(@PathVariable("merchantId") Long merchantId);

}
