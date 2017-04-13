package com.lawu.eshop.member.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.ad.dto.RedPacketDTO;
import com.lawu.eshop.authorization.util.UserUtil;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.member.api.service.RedPacketService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;

/**
 * 红包
 * @author zhangrc
 * @date 2017/4/12
 *
 */
@Api(tags = "redPacket")
@RestController
@RequestMapping(value = "redPacket/")
public class RedPacketController extends BaseController{
	
	@Autowired
	private RedPacketService redPacketService;
	
	
	//@Authorization
    @ApiOperation(value = "领取红包", notes = "领取红包[]（张荣成）", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @RequestMapping(value = "getRedPacket", method = RequestMethod.GET)
    public Result<RedPacketDTO> getRedPacket(/*@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,*/@RequestParam @ApiParam(required = true, value = "商家id") Long merchantId) {
    	Long memberId = UserUtil.getCurrentUserId(getRequest());
    	Result<RedPacketDTO> rs=redPacketService.getRedPacket(merchantId,memberId);
    	
    	return rs;
    }

}
