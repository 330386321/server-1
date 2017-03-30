package com.lawu.eshop.member.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.authorization.annotation.Authorization;
import com.lawu.eshop.authorization.util.UserUtil;
import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.constants.UserConstant;
import com.lawu.eshop.member.api.service.PointDetailService;
import com.lawu.eshop.property.dto.PointDetailDTO;
import com.lawu.eshop.property.param.PointDetailQueryParam;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;

/**
 * @author Sunny 
 * @date 2017/3/30
 */
@Api(tags = "pointDetail")
@RestController
@RequestMapping(value = "pointDetail/")
public class PointDetailController extends BaseController {

    @Autowired
    private PointDetailService pointDetailService;
    
    /**
     * 根据用户编号分页获取积分明细列表。
     * 
     * @param token
     * @param param
     * @return
     */
    @ApiOperation(value = "获取积分明细列表", notes = "根据用户编号分页获取积分明细列表。[]（蒋鑫俊）", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @Authorization
    @RequestMapping(value = "page", method = RequestMethod.GET)
    public Result<Page<PointDetailDTO>> page(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token, 
    		@ModelAttribute @ApiParam(name = "param", value = "查询资料") PointDetailQueryParam param) {
    	String userNum = UserUtil.getCurrentUserNum(getRequest());
    	
    	return successGet(pointDetailService.findPageByUserNum(userNum, param));
    }
    
    
}
