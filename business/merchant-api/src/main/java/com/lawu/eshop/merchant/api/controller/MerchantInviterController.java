package com.lawu.eshop.merchant.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.core.page.PageParam;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.merchant.api.service.MerchantService;
import com.lawu.eshop.user.dto.MemberDTO;
import com.lawu.eshop.user.dto.MerchantDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;

/**
 * @author zhangrc
 * @date 2017/3/27
 */
@Api(tags = "merchant")
@RestController
@RequestMapping(value = "merchant/")
public class MerchantInviterController extends BaseController {

    @Autowired
    private MerchantService merchantService;

    @ApiOperation(value = "我推荐的商家", notes = "我推荐的商家查询,[200],（张荣成）", httpMethod = "POST")
    //@Authorization
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "findMemberListByUser", method = RequestMethod.POST)
    public Result<Page<MerchantDTO>> findMemberListByUser(@RequestParam @ApiParam (required = true, value = "邀请人id") Long inviterId ,@ModelAttribute @ApiParam( value = "查询信息") PageParam pageQuery) {
    	 Result<Page<MerchantDTO>>  pageDTOS=merchantService.getMerchantByInviter(inviterId, pageQuery);
    	return pageDTOS;
    }

}
