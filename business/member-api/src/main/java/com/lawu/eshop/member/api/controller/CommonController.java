package com.lawu.eshop.member.api.controller;

import com.lawu.eshop.authorization.annotation.Authorization;
import com.lawu.eshop.authorization.manager.TokenManager;
import com.lawu.eshop.authorization.util.UserUtil;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.constants.UserConstant;
import com.lawu.eshop.framework.web.doc.annotation.Audit;
import com.lawu.eshop.framework.web.dto.TokenDTO;
import com.lawu.eshop.mall.dto.ConfigDTO;
import com.lawu.eshop.member.api.MemberApiConfig;
import com.lawu.eshop.member.api.service.MemberService;
import com.lawu.eshop.user.dto.LoginUserDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Leach
 * @date 2017/3/11
 */
@Api(tags = "common")
@RestController
@RequestMapping(value = "/")
public class CommonController extends BaseController {

    @Autowired
    private MemberApiConfig memberApiConfig;

    @Autowired
    private MemberService memberService;

    @Autowired
    private TokenManager tokenManager;

    @Audit(date = "2017-03-29", reviewer = "孙林青")
    @ApiOperation(value = "登录", notes = "根据账号密码登录，成功返回token。[2000]（孙林青）", httpMethod = "POST")
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    @RequestMapping(value = "login/{account}", method = RequestMethod.POST)
    public Result<TokenDTO> login(@PathVariable @ApiParam(required = true, value = "账号") String account,
                                  @RequestParam @ApiParam(required = true, value = "密码") String pwd) {

        Result<LoginUserDTO> result = memberService.find(account, pwd);
        if (!isSuccess(result)) {
            return successGet(result);
        }

        LoginUserDTO userDTO = result.getModel();

        String token = tokenManager.createToken(UserConstant.MEMBER_TOKEN_TYPE, userDTO.getNum(), userDTO.getId(), userDTO.getAccount());

        TokenDTO tokenDTO = new TokenDTO();
        tokenDTO.setUserNum(userDTO.getNum());
        tokenDTO.setToken(token);
        tokenDTO.setRyToken(userDTO.getRyToken());

        return successCreated(tokenDTO);
    }

    @Audit(date = "2017-03-29", reviewer = "孙林青")
    @ApiOperation(value = "退出", notes = "退出登录，清除token。（孙林青）", httpMethod = "DELETE")
    @ApiResponse(code = HttpCode.SC_NO_CONTENT, message = "success")
    @Authorization
    @RequestMapping(value = "logout", method = RequestMethod.DELETE)
    public Result logout(@RequestHeader(UserConstant.REQ_HEADER_TOKEN) String token) {
        Long userId = UserUtil.getCurrentUserId(getRequest());
        memberService.delUserGtPush(userId);
        tokenManager.delRelationshipByToken(token);
        return successDelete();
    }

    @Audit(date = "2017-04-19", reviewer = "孙林青")
    @ApiOperation(value = "获取配置信息", notes = "获取配置信息。（章勇）", httpMethod = "GET")
    @RequestMapping(value = "getConfig", method = RequestMethod.GET)
    public Result<ConfigDTO> getConfig() {
        ConfigDTO configDTO = new ConfigDTO();
        configDTO.setImageUrl(memberApiConfig.getImageUrl());
        configDTO.setVideoUrl(memberApiConfig.getVideoUrl());
        configDTO.setInviterMemberUrl(memberApiConfig.getInviterMemberUrl());
        configDTO.setInviterMerchantUrl(memberApiConfig.getInviterMerchantUrl());
        return successCreated(configDTO);
    }

}
