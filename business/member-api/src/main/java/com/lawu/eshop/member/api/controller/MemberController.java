package com.lawu.eshop.member.api.controller;

import com.lawu.eshop.authorization.annotation.Authorization;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.member.api.service.MemberService;
import com.lawu.eshop.user.dto.InviterDTO;
import com.lawu.eshop.user.dto.MemberDTO;
import com.lawu.eshop.user.dto.UserDTO;
import com.lawu.eshop.user.param.UserParam;
import com.lawu.eshop.user.query.MemberQuery;
import com.lawu.eshop.user.param.RegisterParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author zhangyong on 2017/3/22.
 */
@Api(tags = "member")
@RestController
@RequestMapping(value = "member/")
public class MemberController extends BaseController {

    @Autowired
    private MemberService memberService;

    @ApiOperation(value = "会员资料信息", notes = "根据会员id获取会员资料信息，成功返回 member （章勇）", httpMethod = "GET")
   // @Authorization
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "findMemberInfo/{memberId}", method = RequestMethod.GET)
    public Result<UserDTO> findMemberInfo(@PathVariable("memberId") @ApiParam(name = "memberId", required = true, value = "会员ID") Long memberId) {
        Result<UserDTO> result = memberService.findMemberInfo(memberId);

            return result;

    }

    @ApiOperation(value = "更新会员资料", notes = "会员修改资料信息（章勇）", httpMethod = "PUT")
    //@Authorization
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @RequestMapping(value = "updateMemberInfo/{id}", method = RequestMethod.PUT)
    public Result updateMemberInfo(@ModelAttribute @ApiParam(required = true, value = "会员信息") UserParam memberParam, @PathVariable("id") @ApiParam(required = true, value = "会员ID") Long id){
        Result r = memberService.updateMemberInfo(memberParam,id);

        return r;
    }

    @ApiOperation(value = "修改登录密码", notes = "根据会员ID修改登录密码。(梅述全)", httpMethod = "PUT")
    @Authorization
    @RequestMapping(value = "updateLoginPwd/{id}", method = RequestMethod.PUT)
    public Result updateLoginPwd(@PathVariable @ApiParam(required = true, value = "id") Long id,
                                 @RequestParam @ApiParam(required = true, value = "原始密码") String originalPwd,
                                 @RequestParam @ApiParam(required = true, value = "新密码") String newPwd) {
        return memberService.updateLoginPwd(id, originalPwd, newPwd);
    }

    @ApiOperation(value = "查询邀请人", notes = "根据账号查询邀请人信息。(梅述全)", httpMethod = "GET")
    @RequestMapping(value = "getInviterByAccount/{account}", method = RequestMethod.GET)
    public Result<InviterDTO> getInviterByAccount(@PathVariable @ApiParam(required = true, value = "邀请人账号") String account) {
        return memberService.getInviterByAccount(account);
    }

    @ApiOperation(value = "我的E友", notes = "我的E有查询", httpMethod = "POST")
    @Authorization
    @RequestMapping(value = "findMemberListByUser", method = RequestMethod.POST)
    public Result<List<MemberDTO>> findMemberListByUser(@RequestBody @ApiParam(required = true, value = "查询信息") MemberQuery query) {
    	Result<List<MemberDTO>> memberDTOS = memberService.findMemberListByUser(query);
    	return memberDTOS;
    }

    @ApiOperation(value = "会员注册", notes = "会员注册", httpMethod = "POST")
    @RequestMapping(value = "register", method = RequestMethod.POST)
    public Result register(@RequestBody @ApiParam(required = true, value = "注册信息") RegisterParam registerParam ) {
        memberService.register(registerParam);
        return successCreated();
    }
}
