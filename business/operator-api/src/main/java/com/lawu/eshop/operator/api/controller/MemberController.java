package com.lawu.eshop.operator.api.controller;

import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.operator.api.service.MemberService;
import com.lawu.eshop.user.dto.MemberDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author meishuquan
 * @date 2017/5/16.
 */
@Api(tags = "member")
@RestController
@RequestMapping(value = "member/")
public class MemberController extends BaseController{

    @Autowired
    private MemberService memberService;

    @ApiOperation(value = "根据会员账号查询会员信息", notes = "根据会员账号查询会员信息。[1002]（梅述全）", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    //@RequiresPermissions("index:store")
    @RequestMapping(value = "getMember/{account}", method = RequestMethod.GET)
    public Result<MemberDTO> getMember(@PathVariable @ApiParam(value = "会员账号") String account) {
        return memberService.getMemberByAccount(account);
    }
}
