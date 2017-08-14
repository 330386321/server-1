package com.lawu.eshop.agent.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.agent.api.service.ReportAreaUserActiveService;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.statistics.dto.UserActiveListDTO;
import com.lawu.eshop.statistics.param.AgentReportParam;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;

/**
 * @author zhangyong
 * @date 2017/8/14.
 */
@Api(tags = "activeUser")
@RestController
@RequestMapping(value = "activeUser/")
public class ReportAreaActiveUserController extends BaseController{

    @Autowired
    private ReportAreaUserActiveService reportAreaUserActiveService;

    @ApiOperation(value = "查询活跃用户日统计列表", notes = "查询活跃用户日统计列表（章勇）", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value ="getAgentUserActiveListDaily" ,method = RequestMethod.GET)
    public Result<List<UserActiveListDTO>> getAgentUserActiveListDaily(@ModelAttribute AgentReportParam param){
        return reportAreaUserActiveService.getAgentUserActiveListDaily(param);
    }

    @ApiOperation(value = "查询活跃用户月统计列表", notes = "查询活跃用户月统计列表（章勇）", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
        @RequestMapping(value ="getAgentUserActiveListMonth" ,method = RequestMethod.GET)
    public Result<List<UserActiveListDTO>> getAgentUserActiveListMonth(@ModelAttribute AgentReportParam param){
        return reportAreaUserActiveService.getAgentUserActiveListMonth(param);
    }
}
