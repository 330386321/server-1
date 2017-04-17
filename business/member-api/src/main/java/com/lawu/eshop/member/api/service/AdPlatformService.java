package com.lawu.eshop.member.api.service;

import java.util.List;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.lawu.eshop.ad.constants.PositionEnum;
import com.lawu.eshop.ad.dto.AdPlatformDTO;
import com.lawu.eshop.ad.param.AdPlatformParam;
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
	 * 根据不同的位置查询不同的广告
	 * @param positionEnum
	 * @return
	 */
	@RequestMapping(method = RequestMethod.POST, value = "adPlatform/selectByPosition")
	Result<List<AdPlatformDTO>> selectByPosition(@RequestBody PositionEnum positionEnum);

}
