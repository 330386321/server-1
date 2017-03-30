package com.lawu.eshop.member.api.controller;

import com.lawu.eshop.authorization.annotation.Authorization;
import com.lawu.eshop.authorization.util.UserUtil;
import com.lawu.eshop.framework.core.page.Page;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.constants.UserConstant;
import com.lawu.eshop.member.api.service.InviterService;
import com.lawu.eshop.member.api.service.MemberService;
import com.lawu.eshop.member.api.service.MerchantInviterService;
import com.lawu.eshop.user.dto.InviterDTO;
import com.lawu.eshop.user.dto.MemberDTO;
import com.lawu.eshop.user.dto.MerchantInviterDTO;
import com.lawu.eshop.user.query.MemberQuery;
import com.lawu.eshop.user.query.MerchantInviterParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author zhangrc
 * @date 2017/3/27
 */
@Api(tags = "inviter")
@RestController
@RequestMapping(value = "inviter/")
public class InviterController extends BaseController {

    @Autowired
    private MerchantInviterService merchantInviterService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private InviterService inviterService;

    @ApiOperation(value = "我推荐的商家", notes = "我推荐的商家查询,[200]（张荣成）", httpMethod = "POST")
    @ApiOperation(value = "我推荐的商家", notes = "我推荐的商家查询,[1000|1001]（张荣成）", httpMethod = "POST")
    @Authorization
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "selectInviterMerchant", method = RequestMethod.POST)
    public Result<Page<MerchantInviterDTO>> selectInviterMerchant(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token,
                                                                 @ModelAttribute @ApiParam( value = "查询信息") MerchantInviterParam pageQuery) {
    	Long userId=UserUtil.getCurrentUserId(getRequest());
    	Result<Page<MerchantInviterDTO>>  pageDTOS=merchantInviterService.getMerchantByInviter(userId,pageQuery);
    	return pageDTOS;
    }


    @ApiOperation(value = "我的E友", notes = "我的E有查询,[1000|1001]（张荣成）", httpMethod = "POST")
    @Authorization
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "selectInviterMember", method = RequestMethod.POST)
    public Result<Page<MemberDTO>> findMemberListByUser(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token, @ModelAttribute @ApiParam(required = true, value = "查询信息") MemberQuery query) {
        Long userId = UserUtil.getCurrentUserId(getRequest());
        Result<Page<MemberDTO>> page = memberService.findMemberListByUser(userId, query);
        return page;
    }

    @ApiOperation(value = "查询邀请人", notes = "根据账号查询邀请人信息。[1002] (梅述全)", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "getInviter/{account}", method = RequestMethod.GET)
    public Result<InviterDTO> getInviterByAccount(@PathVariable @ApiParam(required = true, value = "邀请人账号") String account) {
        return inviterService.getInviterByAccount(account);
    }

}
