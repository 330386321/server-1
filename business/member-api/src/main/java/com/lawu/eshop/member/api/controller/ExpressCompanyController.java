package com.lawu.eshop.member.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.authorization.annotation.Authorization;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.constants.UserConstant;
import com.lawu.eshop.mall.dto.ExpressCompanyDTO;
import com.lawu.eshop.member.api.service.ExpressCompanyService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;

/**
 * @author Sunny 
 * @date 2017/4/12
 */
@Api(tags = "expressCompany")
@RestController
@RequestMapping(value = "expressCompany/")
public class ExpressCompanyController extends BaseController {

    @Autowired
    private ExpressCompanyService expressCompanyService;
    
    /**
     * 查询全部快递公司，根据ordinal排序。
     * 
     * @param token
     * @return
     */
    @SuppressWarnings("unchecked")
	@ApiOperation(value = "查询全部快递公司", notes = "查询全部快递公司，根据ordinal排序。[]（蒋鑫俊）", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @Authorization
    @RequestMapping(value = "list", method = RequestMethod.GET)
    public Result<List<ExpressCompanyDTO>> list(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token) {
    	return successGet(expressCompanyService.list());
    }
}
