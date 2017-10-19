package com.lawu.eshop.member.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.ad.dto.GetPlatformRedPacketDTO;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.member.api.service.MemberService;
import com.lawu.eshop.member.api.service.PlatformRedPacketService;
import com.lawu.eshop.user.dto.MemberDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;

/**
 * 描述：平台红包
 *
 * @author zhangrc
 * @date 2017/10/19
 */
@Api(tags = "platformRedPacket")
@RestController
@RequestMapping(value = "platformRedPacket/")
public class PlatformRedPacketController extends BaseController{
	
	
	@Autowired
	private PlatformRedPacketService platformRedPacketService;
	
	@Autowired
    private MemberService memberService;
	
	@ApiOperation(value = "领取平台红包", notes = "领取平台红包,[]（张荣成）", httpMethod = "POST")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "getRedPacket", method = RequestMethod.POST)
    public Result getRedPacket(@RequestParam @ApiParam(required = true, value = "账号") String account) {
		
		Result<MemberDTO> accountResult = memberService.getMemberByAccount(account);
		if(!isSuccess(accountResult)){
			return successCreated(accountResult.getRet());
		}
		Result<GetPlatformRedPacketDTO>  result =new Result<>() ;
		
		if(accountResult.getModel()!=null){
			 result = platformRedPacketService.getRedPacket(accountResult.getModel().getNum());
		}
		
		return result;
    }

	
}
