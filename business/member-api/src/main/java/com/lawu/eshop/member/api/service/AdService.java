package com.lawu.eshop.member.api.service;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.lawu.eshop.ad.dto.AdDTO;
import com.lawu.eshop.ad.param.AdMemberParam;
import com.lawu.eshop.ad.param.AdPraiseParam;
import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.Result;

@FeignClient(value = "ad-srv")
public interface AdService {
	

	/**
	 * 查询广告
	 * @param adMerchantParam
	 * @param memberId
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "ad/selectListByMember")
    Result<Page<AdDTO>> selectListByMember(@RequestBody AdMemberParam adMemberParam);
	
	
	 /**
	  * 单个查询地址
	  * @return
	  */
	@RequestMapping(method = RequestMethod.GET, value = "ad/selectAbById/{id}")
	Result<AdDTO> selectAbById(@PathVariable("id") Long id);
	
	/**
	 * E赞查询
	 * @param adPraiseParam
	 * @return
	 */
	@RequestMapping(value = "ad/selectPraiseListByMember", method = RequestMethod.POST)
    public Result<Page<AdDTO>> selectPraiseListByMember(@RequestBody AdPraiseParam adPraiseParam);
	
	/**
	 * E赞前三名
	 * @param adId
	 * @return
	 */
	@RequestMapping(value = "pointPool/selectMemberList", method = RequestMethod.POST)
    public Result<List<Long>> selectMemberList(@RequestParam("id") Long id);
	

}
