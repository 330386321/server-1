package com.lawu.eshop.member.api.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.authorization.annotation.Authorization;
import com.lawu.eshop.authorization.util.UserUtil;
import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.constants.UserConstant;
import com.lawu.eshop.member.api.service.FavoriteMerchantService;
import com.lawu.eshop.user.dto.FavoriteMerchantDTO;
import com.lawu.eshop.user.query.FavoriteMerchantParam;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;

/**
 * 商家收藏
 * @author zhangrc
 * @date 2017/03/27
 *
 */
@Api(tags = "favoriteMerchant")
@RestController
@RequestMapping(value = "favoriteMerchant/")
public class FavoriteMerchantController extends BaseController{
	
	@Resource
	private FavoriteMerchantService favoriteMerchantService;
	
	 /**
	  * 添加商家收藏
	  * @return
	  */
	@Authorization
	@ApiOperation(value = "商家收藏", notes = "商家收藏（张荣成）", httpMethod = "PUT")
	@ApiResponse(code = HttpCode.SC_CREATED, message = "success")
	@RequestMapping(value = "save", method = RequestMethod.PUT)
    public Result save(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,
					   @RequestParam @ApiParam (required = true, value = "商家id") Long merchantId ) {
	   Long memberId=UserUtil.getCurrentUserId(getRequest());
	   Result rs=favoriteMerchantService.save(memberId,merchantId);
	   return rs;
    }
	
	@ApiOperation(value = "我收藏的商家", notes = "我收藏商家列表查询,[200],（张荣成）", httpMethod = "POST")
    @Authorization
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "getMyFavoriteMerchant", method = RequestMethod.POST)
    public Result<Page<FavoriteMerchantDTO>> findMemberListByUser(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,
																  @ModelAttribute @ApiParam(value = "查询信息") FavoriteMerchantParam pageQuery) {
		Long memberId=UserUtil.getCurrentUserId(getRequest());
    	Result<Page<FavoriteMerchantDTO>> page = favoriteMerchantService.getMyFavoriteMerchant(memberId,pageQuery);
    	return page;
    }

}
