package com.lawu.eshop.agent.srv.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.agent.dto.LoginUserDTO;
import com.lawu.eshop.agent.srv.bo.AgentUserBO;
import com.lawu.eshop.agent.srv.converter.AgentUserConverter;
import com.lawu.eshop.agent.srv.service.AgentUserService;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;

/**
 * @author zhangyong
 * @date 2017/8/9
 */
@RestController
@RequestMapping(value = "user/")
public class AgentUserController extends BaseController {

    @Autowired
    private AgentUserService agentUserService;

    @RequestMapping(value = "withPwd/{account}", method = RequestMethod.POST)
    public Result<LoginUserDTO> login(@PathVariable("account") String account, @RequestParam("pwd") String pwd) {
        AgentUserBO userBO = agentUserService.find(account, pwd);
        if (userBO == null) {
            return successGet(ResultCode.MEMBER_WRONG_PWD);
        }
        return successCreated(AgentUserConverter.convertDTO(userBO));
    }


}
