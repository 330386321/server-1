package com.lawu.eshop.operator.api.service;

import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.statistics.dto.ReportUserActiveAreaDTO;
import com.lawu.eshop.statistics.dto.UserActiveListDTO;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author zhangyong
 * @date 2017/6/30.
 */
@FeignClient(value = "statistics-srv")
public interface UserActiveService {

    @RequestMapping(value = "userActive/getUserActiveListDaily",method = RequestMethod.GET)
    Result<List<UserActiveListDTO>> getUserActiveListDaily(@RequestParam("beginTime") String beginTime,
                                                           @RequestParam("endTime") String endTime);

    @RequestMapping(value = "userActive/getUserActiveListMonth",method = RequestMethod.GET)
    Result<List<UserActiveListDTO>> getUserActiveListMonth(@RequestParam("beginTime") String beginTime,
                                                           @RequestParam("endTime") String endTime);

    @RequestMapping(value = "userActive/getReportUserActiveAreaDailyList",method = RequestMethod.GET)
    Result<List<ReportUserActiveAreaDTO>> getReportUserActiveAreaDailyList(@RequestParam(value = "reportDate") String reportDate);

    @RequestMapping(value = "userActive/getReportUserActiveAreaMonthList",method = RequestMethod.GET)
    Result<List<ReportUserActiveAreaDTO>> getReportUserActiveAreaMonthList(@RequestParam(value = "reportDate") String reportDate);
}
