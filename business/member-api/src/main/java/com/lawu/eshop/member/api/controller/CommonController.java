package com.lawu.eshop.member.api.controller;

import com.lawu.eshop.authorization.annotation.Authorization;
import com.lawu.eshop.authorization.manager.TokenManager;
import com.lawu.eshop.authorization.util.UserUtil;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.member.api.constants.UserConstant;
import com.lawu.eshop.member.api.dto.TokenDTO;
import com.lawu.eshop.member.api.service.MemberService;
import com.lawu.eshop.user.dto.UserDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Leach
 * @date 2017/3/11
 */
@Api(tags = "common")
@RestController
@RequestMapping(value = "/")
public class CommonController extends BaseController {

    @Autowired
    private MemberService memberService;

    @Autowired
    private TokenManager tokenManager;

    @ApiOperation(value = "登录", notes = "根据账号密码登录，成功返回token。[200|2000]）", httpMethod = "POST")
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public Result<TokenDTO> login(@ApiParam(required = true, value = "账号") String account,
                                  @ApiParam(required = true, value = "密码") String pwd) {

        UserDTO userDTO = memberService.find(account, pwd);
        if (userDTO == null) {
            return response(MemberResultCode.MEMBER_WRONG_PWD);
        }
        if (userDTO.getId() < 1) {
            return failResponse(ResultCode.NATIVE_BAD_REQUEST, "会员查询内部接口调用异常");
        }
        String token = tokenManager.createToken(UserConstant.TOKEN_TYPE, userDTO.getId(), userDTO.getAccount());

        TokenDTO tokenDTO = new TokenDTO();
        tokenDTO.setToken(token);

        return successResponse(tokenDTO);
    }

    @ApiOperation(value = "退出", notes = "退出登录，清除token。[200]）", httpMethod = "GET")
    @Authorization
    @RequestMapping(value = "logout")
    public Result logout(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token) {
        //tokenManager.delRelationshipByToken(token);
        return successResponse();
    }

    @ApiOperation(value = "测试", notes = "测试。[200]）", httpMethod = "GET")
    @Authorization
    @RequestMapping(value = "test")
    public Result test(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token) {
        return successResponse(UserUtil.getCurrentAccount(getRequest()));
    }
}
