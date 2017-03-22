package com.lawu.eshop.member.api.controller;

import com.lawu.eshop.authorization.annotation.Authorization;
import com.lawu.eshop.member.api.service.MemberService;
import com.lawu.eshop.user.dto.UserDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhangyong on 2017/3/22.
 */
@Api(tags = "member")
@RestController
@RequestMapping(value = "member/")
public class MemberController {

    @Autowired
    private MemberService memberService;

    @ApiOperation(value = "会员资料信息", notes = "根据会员id获取会员资料信息）", httpMethod = "POST")
    @RequestMapping(value = "findmemberinfo", method = RequestMethod.POST)
    public UserDTO findMemberInfo(@ApiParam(name = "memberId", required = true, value = "会员ID") Long memberId) {
        UserDTO userDTO = memberService.findMemberInfo(memberId);

        return userDTO;
    }

    @ApiOperation(value = "修改密码", notes = "会员修改密码）", httpMethod = "POST")
    @Authorization
    @RequestMapping(value = "updatePwd", method = RequestMethod.POST)
    public void updatePwd(@ApiParam(required = true, value = "主键") Long id,
                          @ApiParam(required = true, value = "密码") String pwd) {
        memberService.updatePwd(id, pwd);
    }
}
