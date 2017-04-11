package com.lawu.eshop.merchant.api.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * 广告投放或者用户数据
 * @author zhangrc
 * @date 2017/4/10
 *
 */
@FeignClient(value = "user-srv")
public interface MemberCountService {
	
	/**
	 * 根据地区或者用户数量
	 * @param regionPath
	 * @return
	 */
	@RequestMapping(value = "member/findMemberCount", method = RequestMethod.GET)
    Integer findMemberCount(@RequestParam("areas") String areas);
	
	/**
	 * 根据当前商家获取当前粉丝数量
	 * @param merchantId
	 * @return
	 */
	@RequestMapping(value = "member/findFensCount", method = RequestMethod.GET)
    Integer findFensCount( @RequestParam("merchantId") Long merchantId);

}
