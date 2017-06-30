package com.lawu.eshop.operator.api.controller;

import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.HttpCode;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.mall.dto.RegionDTO;
import com.lawu.eshop.operator.api.service.RegionService;
import com.lawu.eshop.operator.api.service.UserActiveService;
import com.lawu.eshop.statistics.dto.ReportUserActiveAreaDTO;
import com.lawu.eshop.statistics.dto.UserActiveListDTO;
import com.lawu.eshop.statistics.param.UserActiveParam;
import com.lawu.eshop.utils.DateUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
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

    @Autowired
    private RegionService regionService;

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

    @ApiOperation(value = "查询区域日统计列表", notes = "查询区域日统计列表（章勇）", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "getReportUserActiveAreaDailyList", method = RequestMethod.GET)
    public Result<List<ReportUserActiveAreaDTO>> getReportUserActiveAreaDailyList(@RequestParam("reportDate") String reportDate) {
        if(StringUtils.isEmpty(reportDate)){
            reportDate = DateUtil.getDateFormat(new Date(),"yyyy-MM-dd");
        }
        Result<List<ReportUserActiveAreaDTO>> result = userActiveService.getReportUserActiveAreaDailyList(reportDate);
        if (result.getModel() != null && !result.getModel().isEmpty()) {
            for (ReportUserActiveAreaDTO regAreaDTO : result.getModel()) {
                Result<RegionDTO> regionDTOResult = regionService.getRegionById(regAreaDTO.getCityId());
                if (regionDTOResult != null && regionDTOResult.getModel() != null) {
                    regAreaDTO.setLongitude(regionDTOResult.getModel().getLongitude());
                    regAreaDTO.setLatitude(regionDTOResult.getModel().getLatitude());
                }
            }
        }
        return result;
    }

    @ApiOperation(value = "查询区域月统计列表", notes = "查询区域月统计列表（章勇）", httpMethod = "GET")
    @ApiResponse(code = HttpCode.SC_OK, message = "success")
    @RequestMapping(value = "getReportUserActiveAreaMonthList", method = RequestMethod.GET)
    public Result<List<ReportUserActiveAreaDTO>> getReportUserActiveAreaMonthList(@RequestParam("reportDate") String reportDate) {
        if(StringUtils.isEmpty(reportDate)){
            reportDate = DateUtil.getDateFormat(new Date(),"yyyy-MM");
        }
        Result<List<ReportUserActiveAreaDTO>> result = userActiveService.getReportUserActiveAreaMonthList(reportDate);
        if (result.getModel() != null && !result.getModel().isEmpty()) {
            for (ReportUserActiveAreaDTO regAreaDTO : result.getModel()) {
                Result<RegionDTO> regionDTOResult = regionService.getRegionById(regAreaDTO.getCityId());
                if (regionDTOResult != null && regionDTOResult.getModel() != null) {
                    regAreaDTO.setLongitude(regionDTOResult.getModel().getLongitude());
                    regAreaDTO.setLatitude(regionDTOResult.getModel().getLatitude());
                }
            }
        }
        return result;
    }

}
