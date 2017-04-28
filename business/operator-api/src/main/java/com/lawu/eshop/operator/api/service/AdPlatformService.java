package com.lawu.eshop.operator.api.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.lawu.eshop.ad.constants.PositionEnum;
import com.lawu.eshop.ad.dto.AdPlatformDTO;
import com.lawu.eshop.ad.dto.AdPlatformOperatorDTO;
import com.lawu.eshop.ad.param.AdPlatformFindParam;
import com.lawu.eshop.ad.param.AdPlatformParam;
import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.Result;

/**
 * 平台广告管理
 * @author zhangrc
 * @date 2017/4/5
 *
 */
@FeignClient(value = "ad-srv")
public interface AdPlatformService {
	
	/**
	 * 添加广告
	 * @param adPlatformParam
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "adPlatform/saveAdPlatform")
	Result saveAdPlatform(@RequestBody AdPlatformParam adPlatform,@RequestParam("url") String url);
	
	/**
	 * 删除广告
	 * @param id
	 * @return
	 */
	@RequestMapping(method = RequestMethod.DELETE, value = "adPlatform/removeAdPlatform/{id}")
	Result removeAdPlatform(@PathVariable("id") Long id);
	

	/**
	 * 查询
	 * @param param
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "adPlatform/selectList")
	Result<Page<AdPlatformOperatorDTO>> selectList(@RequestBody AdPlatformFindParam param);
	
	
	/**
	 * 发布广告
	 * @param id
	 * @return
	 */
	@RequestMapping(method = RequestMethod.PUT, value = "adPlatform/issueAd/{id}")
	Result issueAd(@PathVariable("id") Long id);
	
	/**
	 * 下架广告
	 * @param id
	 * @return
	 */
	@RequestMapping(method = RequestMethod.PUT, value = "adPlatform/unShelve/{id}")
	Result unShelve(@PathVariable("id") Long id);
	
	/**
	 * 设置广告位
	 * @param id
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "adPlatform/setPosition/{id}")
	Result setPosition(@PathVariable("id") Long id,@RequestBody PositionEnum positionEnum);
	
	/**
	 * 修改
	 * @param id
	 * @param adPlatformParam
	 * @param url
	 * @return
	 */
	@RequestMapping(value = "adPlatform/update/{id}", method = RequestMethod.POST)
    Result update(@PathVariable("id") Long id, @RequestBody AdPlatformParam adPlatformParam,@RequestParam("url") String url); 
	
	
	/**
	 * 单个查询
	 * @param id
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "adPlatform/select/{id}")
	Result<AdPlatformDTO> select(@PathVariable("id") Long id);

}
