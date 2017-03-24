package com.lawu.eshop.member.api.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.lawu.eshop.member.api.service.hystrix.MemberProfileServiceHystrix;

/**
 * 
 * @author zhangrc
 * @date 2017/03/24
 *
 */
@FeignClient(value= "user-srv", fallback = MemberProfileServiceHystrix.class)
public interface MemberProfileService {
	
	/**
	 * 我的E友总数量查询
	 * @param id
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "memberProfile/getMemberCount")
	public Integer getMemberCount(@RequestParam("id")Long id);

	/**
	 * 我推荐的商家总数量
	 * @param id
	 * @return
	 */
	@RequestMapping(method = RequestMethod.GET, value = "memberProfile/getMerchantCount")
	public Integer getMerchantCount(@RequestParam("id")Long id);

}
