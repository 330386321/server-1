package com.lawu.eshop.merchant.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.authorization.annotation.Authorization;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.mall.param.SuggestionParam;
import com.lawu.eshop.merchant.api.service.SuggestionService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;

/**
 * @author Sunny 
 * @date 2017/3/23
 */
@Api(tags = "suggestion")
@RestController
@RequestMapping(value = "suggestion/")
public class SuggestionController extends BaseController {

    @Autowired
    private SuggestionService suggestionService;
    
    @ApiOperation(value = "保存反馈意见", notes = "保存反馈意见。[1000|1004|1005]（蒋鑫俊）", httpMethod = "POST")
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @Authorization
    @RequestMapping(method = RequestMethod.POST)
    public Result<Integer> save(@ModelAttribute @ApiParam(name = "parm", required = true, value = "反馈意见资料") SuggestionParam param) {
    	return successCreated(suggestionService.save(param));
    }
    
}
