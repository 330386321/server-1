package com.lawu.eshop.member.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.member.api.service.SeckillActivityService;
import com.lawu.eshop.product.dto.SeckillActivityThatDayDTO;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;

/**
 * 抢购活动控制器
 * 
 * @author jiangxinjun
 * @createDate 2017年11月24日
 * @updateDate 2017年11月24日
 */
@Api(tags = "seckillActivity")
@RestController
@RequestMapping(path = "seckillActivity/")
public class SeckillActivityController extends BaseController {
    
    @Autowired
    private SeckillActivityService seckillActivityService;
    
    @SuppressWarnings("unchecked")
    @ApiOperation(value = "获取当天的所有活动", notes = "获取当天的所有活动[]（蒋鑫俊）", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(path = "thatday/list", method = RequestMethod.GET)
    public Result<List<SeckillActivityThatDayDTO>> thatDayList() {
        return successGet(seckillActivityService.thatDayList());
    }
    
    @SuppressWarnings("unchecked")
    @ApiOperation(value = "获取最近一天的所有活动", notes = "获取最近一天的所有活动[]（蒋鑫俊）", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(path = "recently/list", method = RequestMethod.GET)
    public Result<List<SeckillActivityThatDayDTO>> recentlyList() {
        return successGet(seckillActivityService.recentlyList());
    }

}
