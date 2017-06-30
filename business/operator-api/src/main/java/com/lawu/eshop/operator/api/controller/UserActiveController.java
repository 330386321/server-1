package com.lawu.eshop.operator.api.controller;

import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.operator.api.service.UserActiveService;
import com.lawu.eshop.statistics.dto.UserActiveListDTO;
import com.lawu.eshop.statistics.param.UserActiveParam;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author zhangyong
 * @date 2017/6/30.
 */
@Api(tags = "userActive")
@RestController
@RequestMapping(value = "userActive/")
public class UserActiveController extends BaseController{

    @Autowired
    private UserActiveService userActiveService;

    @ApiOperation(value = "查询活跃用户日统计列表", notes = "查询活跃用户日统计列表（章勇）", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value ="getUserActiveListDaily" ,method = RequestMethod.GET)
    public Result<List<UserActiveListDTO>> getUserActiveListDaily(@ModelAttribute UserActiveParam param){
        return userActiveService.getUserActiveListDaily(param);
    }

    @ApiOperation(value = "查询活跃用户月统计列表", notes = "查询活跃用户月统计列表（章勇）", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value ="getUserActiveListMonth" ,method = RequestMethod.GET)
    public Result<List<UserActiveListDTO>> getUserActiveListMonth(@ModelAttribute UserActiveParam param){
        return userActiveService.getUserActiveListMonth(param);
    }

}
