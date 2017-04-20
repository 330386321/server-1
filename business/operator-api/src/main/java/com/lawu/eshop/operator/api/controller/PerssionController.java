package com.lawu.eshop.operator.api.controller;

import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.framework.web.ResultCode;
import com.lawu.eshop.operator.api.service.PressionService;
import com.lawu.eshop.operator.dto.PerssionDTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhangyong
 * @date 2017/4/20.
 */
@Api(tags = "pression")
@RestController
@RequestMapping(value = "pression")
public class PerssionController extends BaseController{
    @Autowired
    private PressionService pressionService;

    @ApiOperation(value = "查询权限信息", notes = "查询权限信息 （章勇）", httpMethod = "GET")
    @RequestMapping(value = "getPerssion",method = RequestMethod.GET)
    @ApiResponse(code = HttpCode.SC_OK,message = "success")
    public Result<PerssionDTO> getPerssion(){
        Subject subject = SecurityUtils.getSubject();
        // 取身份信息
        if(subject.getPrincipals() == null){
            return successGet(ResultCode.USER_NOT_LOGIN);
        }
        String account = subject.getPrincipals().toString();
        Result<PerssionDTO> perssions = pressionService.findPessionByAccount(account);
        return perssions;
    }
}
