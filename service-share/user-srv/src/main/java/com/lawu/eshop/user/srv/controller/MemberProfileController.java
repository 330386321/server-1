package com.lawu.eshop.user.srv.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.user.param.AddressParam;
import com.lawu.eshop.user.srv.service.MemberProfileService;

/**
 * 
 * @author zhangrc
 * @date 2017/03/24
 *
 */
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
   public Integer getMemberCount(@RequestParam Long id) {
	   Integer count=memberProfileService.getMemberCount(id);
	   return count;
   }
   
   /**
    * 我的商家推荐总数量
    * @param id
    * @return
    */
   @RequestMapping(value = "getMerchantCount", method = RequestMethod.GET)
   public Integer getMerchantCount(@RequestParam Long id) {
	   Integer count=memberProfileService.getMerchantCount(id);
	   return count;
   }

}
