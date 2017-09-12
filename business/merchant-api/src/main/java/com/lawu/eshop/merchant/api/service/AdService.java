package com.lawu.eshop.merchant.api.service;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.lawu.eshop.ad.dto.AdDTO;
import com.lawu.eshop.ad.dto.AdDetailDTO;
import com.lawu.eshop.ad.dto.AdMerchantDTO;
import com.lawu.eshop.ad.dto.AdMerchantDetailDTO;
import com.lawu.eshop.ad.dto.IsExistsRedPacketDTO;
import com.lawu.eshop.ad.dto.IsMyDateDTO;
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
	Result<Integer> saveAd(@RequestBody AdSaveParam adSaveParam);
	
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
	Result<IsExistsRedPacketDTO> isExistsRedPacket(@PathVariable("merchantId") Long merchantId);
	
	/**
	 * 商家批量删除广告
	 * @param ids
	 * @return
	 */
	@RequestMapping(value = "ad/batchDeleteAd", method = RequestMethod.DELETE)
	public Result batchDeleteAd(@RequestParam("ids") List<Long> ids,@RequestParam("merchantId") Long merchantId);
	
	/**
	 * 广告详情
	 * @param id
	 * @return
	 */
	@RequestMapping(value = "ad/selectDetail/{id}", method = RequestMethod.GET)
	public Result<AdDetailDTO> selectDetail(@PathVariable("id") Long id);
	
	/**
	 * 判断数据是否是当前的用户
	 * @param id
	 * @param merchantId
	 * @return
	 */
	@RequestMapping(value = "ad/isMyData/{id}", method = RequestMethod.GET)
	Result<IsMyDateDTO> isMyData(@PathVariable("id") Long id,@RequestParam("merchantId") Long merchantId);
	
	

}
