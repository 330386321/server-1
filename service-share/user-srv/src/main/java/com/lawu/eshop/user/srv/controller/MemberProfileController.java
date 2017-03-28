package com.lawu.eshop.user.srv.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.user.dto.MemberProfileDTO;
import com.lawu.eshop.user.srv.service.MemberProfileService;

/**
 * 
 * @author zhangrc
 * @date 2017/03/24
 *
 */
@RestController
@RequestMapping(value = "memberProfile/")
public class MemberProfileController extends BaseController{
	
	@Resource
	private  MemberProfileService memberProfileService;
	
	
	/**
	 * 我的E友总数量
	 * @return
	 */
   @RequestMapping(value = "getMemberCount", method = RequestMethod.GET)
   public Result<MemberProfileDTO> getMemberCount(@RequestParam Long id) {
	   Integer count=memberProfileService.getMemberCount(id);
	   MemberProfileDTO dto=new MemberProfileDTO();
	   dto.setInviteMemberCount(count);
	   return successGet(dto);
   }
   
   /**
    * 我的商家推荐总数量
    * @param id
    * @return
    */
   @RequestMapping(value = "getMerchantCount", method = RequestMethod.GET)
   public Result<MemberProfileDTO> getMerchantCount(@RequestParam Long id) {
	   Integer count=memberProfileService.getMerchantCount(id);
	   MemberProfileDTO dto=new MemberProfileDTO();
	   dto.setInviteMemberCount(count);
	   return successGet(dto);
   }

}
