package com.lawu.eshop.agent.api.controller;

import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.property.dto.ConfigDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Leach
 * @date 2017/4/18
 */
@Api(tags = "common")
@RestController
@RequestMapping(value = "/")
public class CommonController extends BaseController {

    @Value(value="${swagger.api.imagePathUrl}")
    private String imagePathUrl;
    @Value(value="${swagger.api.videoPathUrl}")
    private String videoPathUrl;


    @ApiOperation(value = "登录", notes = "根据账号密码登录。[2000]（孙林青）", httpMethod = "POST")
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public Result login(String account, String password) {
        try {
            SecurityUtils.getSubject().login(new UsernamePasswordToken(account, password));
            return successCreated();
        } catch (AuthenticationException e) {

            return successCreated(ResultCode.MEMBER_WRONG_PWD);
        }
    }

    @ApiOperation(value = "退出", notes = "退出登录。（孙林青）", httpMethod = "DELETE")
    @RequestMapping(value = "logout", method = RequestMethod.DELETE)
    public Result logout() {
        SecurityUtils.getSubject().logout();
        return successCreated();
    }

    @ApiOperation(value = "获取配置信息", notes = "获取配置信息。（章勇）", httpMethod = "GET")
    @RequestMapping(value = "getConfig", method = RequestMethod.GET)
    public Result<ConfigDTO> getConfig() {
        ConfigDTO configDTO = new ConfigDTO();
        configDTO.setImagePathUrl(imagePathUrl);
        configDTO.setVideoPathUrl(videoPathUrl);
        return successCreated(configDTO);
    }
}
