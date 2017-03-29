package com.lawu.eshop.member.api.controller;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.authorization.annotation.Authorization;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.member.api.service.BankService;
import com.lawu.eshop.property.dto.BankDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;

/**
 * @author zhangrc
 * @date 2017/3/29
 */
@Api(tags = "bank")
@RestController
@RequestMapping(value = "member/bank/")
public class BankController extends BaseController{
	
	@Resource 
	private BankService bankService;
	
	/**
	 * 查询所有银行
	 * @return
	 */
	@Authorization
	@ApiOperation(value = "银行数据信息查询", notes = "银行数据信息查询[]（张荣成）", httpMethod = "GET")
	@ApiResponse(code = HttpCode.SC_OK, message = "success")
	@RequestMapping(value = "all", method = RequestMethod.GET)
    public Result<List<BankDTO>> findBank() {
		Result<List<BankDTO>> DTOS = bankService.findBank();
		return  DTOS;
    }

}
