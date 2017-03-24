package com.lawu.eshop.member.api.controller;

import com.lawu.eshop.authorization.annotation.Authorization;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.member.api.service.MemberService;
import com.lawu.eshop.user.dto.InviterDTO;
import com.lawu.eshop.user.dto.UserDTO;
import com.lawu.eshop.user.dto.param.UserParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author zhangyong on 2017/3/22.
 */
@Api(tags = "member")
@RestController
@RequestMapping(value = "member/")
public class MemberController extends BaseController {

    @Autowired
    private MemberService memberService;

    @ApiOperation(value = "会员资料信息", notes = "根据会员id获取会员资料信息）", httpMethod = "POST")
   // @Authorization
    @RequestMapping(value = "findMemberInfo", method = RequestMethod.POST)
    public Result<UserDTO> findMemberInfo(@RequestParam @ApiParam(name = "memberId", required = true, value = "会员ID") Long memberId) {
        UserDTO userDTO = memberService.findMemberInfo(memberId);
        if (userDTO == null){
            return successResponse();
        }else{
            return successResponse(userDTO);
        }
    }

    @ApiOperation(value = "更新会员资料", notes = "会员修改资料信息）", httpMethod = "POST")
    //@Authorization
    @RequestMapping(value = "updateMemberInfo", method = RequestMethod.POST)
    public void updateMemberInfo(@ModelAttribute @ApiParam(required = true, value = "会员信息") UserParam memberParam){
        memberService.updateMemberInfo(memberParam);
    }

    @ApiOperation(value = "修改密码", notes = "会员修改密码", httpMethod = "POST")
    @Authorization
    @RequestMapping(value = "updatePwd", method = RequestMethod.POST)
    public Result updatePwd(@RequestParam @ApiParam(required = true, value = "主键") Long id,
                            @RequestParam @ApiParam(required = true, value = "密码") String pwd) {
        memberService.updatePwd(id, pwd);
        return successResponse();
    }

    @ApiOperation(value = "查询邀请人", notes = "根据账号查询邀请人信息", httpMethod = "GET")
    @RequestMapping(value = "getInviterByAccount", method = RequestMethod.GET)
    public Result<InviterDTO> getInviterByAccount(@RequestParam @ApiParam(required = true, value = "邀请人账号") String account) {
        InviterDTO inviterDTO = memberService.getInviterByAccount(account);
        if (inviterDTO == null) {
            return successResponse();
        }
        if (inviterDTO.getInviterId() < 1) {
            return failResponse(ResultCode.NATIVE_BAD_REQUEST, "查询邀请人信息调用异常");
        }
        return successResponse(inviterDTO);
    }

    @ApiOperation(value = "我的E友", notes = "我的E有查询", httpMethod = "GET")
    @Authorization
    @RequestMapping(value = "findMemberListByUser", method = RequestMethod.GET)
    public void findMemberListByUserId(@ModelAttribute @ApiParam(required = true, value = "当前用户id") Long userId) {
        memberService.findMemberListByUser(userId);
    }
}
