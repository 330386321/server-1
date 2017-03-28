package com.lawu.eshop.member.api.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.authorization.annotation.Authorization;
import com.lawu.eshop.authorization.util.UserUtil;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.member.api.service.MemberProfileService;
import com.lawu.eshop.user.dto.MemberProfileDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;

/**
 * 
 * @author zhangrc
 * @date 2017/03/24
 *
 */
@Api(tags = "memberProfile")
@RestController
@RequestMapping(value = "memberProfile/")
public class MemberProfileController extends BaseController{
	
	@Resource
	private  MemberProfileService memberProfileService;
	
	
	/**
	 * 我的E友总数量
	 * @return
	 */
	@ApiOperation(value = "我的E友总数量", notes = "我的E友总数量,[200]（张荣成）", httpMethod = "GET")
    @Authorization
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "getMemberCount", method = RequestMethod.GET)
    public Result<MemberProfileDTO> getMemberCount() {
	   Long id=UserUtil.getCurrentUserId(getRequest());
	   Result<MemberProfileDTO> rdto=memberProfileService.getMemberCount(id);
	   return rdto;
    }
   
   /**
    * 我的商家推荐总数量
    * @param id
    * @return
    */
   @ApiOperation(value = "我的商家推荐总数量", notes = "我的商家推荐总数量,[200]（张荣成）", httpMethod = "GET")
   @Authorization
   @ApiResponse(code = HttpCode.SC_OK, message = "success")
   @RequestMapping(value = "getMerchantCount", method = RequestMethod.GET)
   public Result<MemberProfileDTO> getMerchantCount() {
	   Long id=UserUtil.getCurrentUserId(getRequest());
	   Result<MemberProfileDTO> rdto=memberProfileService.getMerchantCount(id);
	   return rdto;
   }

}
