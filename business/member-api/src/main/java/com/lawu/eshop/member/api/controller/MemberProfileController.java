package com.lawu.eshop.member.api.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.member.api.service.MemberProfileService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;

/**
 * 
 * @author zhangrc
 * @date 2017/03/24
 *
 */
@Api(tags = "memberProfile")
@RestController
@RequestMapping(value = "memberProfile/")
public class MemberProfileController {
	
	@Resource
	private  MemberProfileService memberProfileService;
	
	
	/**
	 * 我的E友总数量
	 * @return
	 */
   @RequestMapping(value = "getMemberCount", method = RequestMethod.GET)
   public Integer getMemberCount(@RequestParam @ApiParam(required = true, value = "会员id") Long id) {
	   Integer count=memberProfileService.getMemberCount(id);
	   return count;
   }
   
   /**
    * 我的商家推荐总数量
    * @param id
    * @return
    */
   @RequestMapping(value = "getMerchantCount", method = RequestMethod.GET)
   public Integer getMerchantCount(@RequestParam @ApiParam(required = true, value = "会员id") Long id) {
	   Integer count=memberProfileService.getMerchantCount(id);
	   return count;
   }

}
