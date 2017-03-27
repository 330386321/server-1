package com.lawu.eshop.member.api.controller;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.member.api.service.FavoriteMerchantService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;

/**
 * 商家收藏
 * @author zhangrc
 * @date 2017/03/27
 *
 */
@RestController
@RequestMapping(value = "favoriteMerchant/")
public class FavoriteMerchantController extends BaseController{
	
	@Resource
	private FavoriteMerchantService favoriteMerchantService;
	
	 /**
	  * 添加商家收藏
	  * @return
	  */
	//@Authorization
	@ApiOperation(value = "商家收藏", notes = "商家收藏（张荣成）", httpMethod = "POST")
	@ApiResponse(code = HttpCode.SC_CREATED, message = "success")
	@RequestMapping(value = "save", method = RequestMethod.POST)
    public Result save(@RequestParam @ApiParam (required = true, value = "会员id")  Long memberId ,@RequestParam @ApiParam (required = true, value = "商家id") Long merchantId ) {
	   Result rs=favoriteMerchantService.save(memberId,merchantId);
	   return rs;
    }

}
