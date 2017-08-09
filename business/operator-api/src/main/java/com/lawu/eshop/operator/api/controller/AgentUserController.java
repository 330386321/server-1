package com.lawu.eshop.operator.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.agent.param.AgentUserParam;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.operator.api.service.AgentUserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;

/**
 * @author zhangyong
 * @date 2017/8/9.
 */
@Api(tags = "agentUser")
@RestController
@RequestMapping(value = "agentUser/")
public class AgentUserController extends BaseController{

    @Autowired
    private AgentUserService agentUserService;

    @ApiOperation(value = "添加代理商账号", notes = "添加代理商账号。[1000，8111，8112]（章勇）", httpMethod = "POST")
    //@RequiresPermissions("adAudit:list")
    @RequestMapping(value = "addAgentUser", method = RequestMethod.POST)
    @ApiResponse(code = HttpCode.SC_CREATED, message = "success")
    public Result addAgentUser(@ModelAttribute @ApiParam AgentUserParam param) {

        return agentUserService.addAgentUser(param);
    }
}
