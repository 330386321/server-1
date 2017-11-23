package com.lawu.eshop.merchant.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.authorization.annotation.Authorization;
import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.constants.UserConstant;
import com.lawu.eshop.merchant.api.service.SeckillActivityJoinService;
import com.lawu.eshop.product.dto.SeckillActivityJoinDTO;
import com.lawu.eshop.product.param.SeckillActivityJoinParam;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * 
 * @author zhangrc
 * @date 2017/11/23
 *
 */
@Api(tags = "seckillActivityJoin")
@RestController
@RequestMapping(value = "seckillActivityJoin/")
public class SeckillActivityJoinController {
	
	@Autowired
	private SeckillActivityJoinService seckillActivityJoinService;
	
	@ApiOperation(value = "分页查询专场活动", notes = "专场活动，[]。(张荣成)", httpMethod = "GET")
	@Authorization
	@RequestMapping(value = "queryPage", method = RequestMethod.GET)
	public Result<Page<SeckillActivityJoinDTO>>  queryPage(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,
			@ModelAttribute @ApiParam SeckillActivityJoinParam param) {
		return seckillActivityJoinService.queryPage(param);
	}

}
