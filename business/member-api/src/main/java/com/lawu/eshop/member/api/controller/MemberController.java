package com.lawu.eshop.member.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.authorization.annotation.Authorization;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.member.api.service.MemberService;
import com.lawu.eshop.user.dto.InviterDTO;
import com.lawu.eshop.user.dto.UserDTO;
import com.lawu.eshop.user.param.UserParam;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;

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
        UserDTO userDTO = memberService.findMemberInfo(memberId);
        if (userDTO == null){
            return successGet();
        }else{
            return successGet(userDTO);
        }
    }

    @ApiOperation(value = "更新会员资料", notes = "会员修改资料信息（章勇）", httpMethod = "PUT")
    //@Authorization
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @RequestMapping(value = "updateMemberInfo/{id}", method = RequestMethod.PUT)
    public Result updateMemberInfo(@RequestBody @ApiParam(required = true, value = "会员信息") UserParam memberParam, @PathVariable("id") Long id){
        int row = memberService.updateMemberInfo(memberParam,id);
        if(row == 1){
            return successCreated();
        }else {
            return successCreated(ResultCode.USER_WRONG_ID);
        }
    }

    @ApiOperation(value = "修改密码", notes = "会员修改密码", httpMethod = "POST")
    @Authorization
    @RequestMapping(value = "updatePwd", method = RequestMethod.POST)
    public Result updatePwd(@RequestParam @ApiParam(required = true, value = "主键") Long id,
                            @RequestParam @ApiParam(required = true, value = "密码") String pwd) {
        memberService.updatePwd(id, pwd);
        return successCreated();
    }

    @ApiOperation(value = "查询邀请人", notes = "根据账号查询邀请人信息", httpMethod = "GET")
    @RequestMapping(value = "getInviterByAccount", method = RequestMethod.GET)
    public Result<InviterDTO> getInviterByAccount(@RequestParam @ApiParam(required = true, value = "邀请人账号") String account) {
        InviterDTO inviterDTO = memberService.getInviterByAccount(account);
        if (inviterDTO == null) {
            return successGet();
        }
        if (inviterDTO.getInviterId() < 1) {
            return failServerError( "查询邀请人信息调用异常");
        }
        return successGet(inviterDTO);
    }

    @ApiOperation(value = "我的E友", notes = "我的E有查询", httpMethod = "GET")
    @Authorization
    @RequestMapping(value = "findMemberListByUser", method = RequestMethod.GET)
    public void findMemberListByUserId(@RequestParam @ApiParam(required = true, value = "用户id") Long userId
    		,@RequestParam @ApiParam(required = false, value = "当前页") Long currentPage
    		,@RequestParam @ApiParam(required = false, value = "查询条件") String accountOrName) {
    	
       // memberService.findMemberListByUser(userId,currentPage,accountOrName);
    }
}
