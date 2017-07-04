package com.lawu.eshop.statistics.srv.controller;

import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.statistics.dto.ReportUserActiveAreaDTO;
import com.lawu.eshop.statistics.dto.UserActiveDTO;
import com.lawu.eshop.statistics.dto.UserActiveListDTO;
import com.lawu.eshop.statistics.srv.bo.ReportUserActiveAreaDailyBO;
import com.lawu.eshop.statistics.srv.bo.ReportUserActiveAreaMonthBO;
import com.lawu.eshop.statistics.srv.bo.ReportUserActiveBO;
import com.lawu.eshop.statistics.srv.bo.UserActiveBO;
import com.lawu.eshop.statistics.srv.converter.UserActiveConverter;
import com.lawu.eshop.statistics.srv.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author zhangyong
 * @date 2017/6/29.
 */
@RestController
@RequestMapping(value = "userActive/")
public class UserActiveController extends BaseController{

    @Autowired
    private UserActiveService userActiveService;

    @Autowired
    private ReportUserActiveMonthService reportUserActiveMonthService;

    @Autowired
    private ReportUserActiveDailyService reportUserActiveDailyService;


    @Autowired
    private ReportUserActiveAreaDailyService reportUserActiveAreaDailyService;

    @Autowired
    private ReportUserActiveAreaMonthService reportUserActiveAreaMonthService;

    @RequestMapping(value = "collectionMemberActiveDaily", method = RequestMethod.GET)
    public Result<Integer> collectionMemberActiveDaily(){

        Integer count = userActiveService.collectionMemberActiveDaily();
        return successGet(count);
    }

    @RequestMapping(value = "collectionMerchantActiveDaily", method = RequestMethod.GET)
    public Result<Integer> collectionMerchantActiveDaily(){
        Integer count = userActiveService.collectionMerchantActiveDaily();
        return successGet(count);
    }

    @RequestMapping(value = "saveUserActiveDaily", method = RequestMethod.POST)
    public Result saveUserActiveDaily(@RequestParam(value = "memberCount") Integer memberCount,
                               @RequestParam(value = "merchantCount") Integer merchantCount){
        reportUserActiveDailyService.saveUserActiveDaily(memberCount, merchantCount);
        return  successCreated();
    }

    @RequestMapping(value = "collectionMemberActiveMonth", method = RequestMethod.GET)
    public Result<Integer> collectionMemberActiveMonth(){

        Integer count = userActiveService.collectionMemberActiveMonth();
        return successGet(count);
    }

    @RequestMapping(value = "collectionMerchantActiveMonth", method = RequestMethod.GET)
    public Result<Integer> collectionMerchantActiveMonth(){
        Integer count = userActiveService.collectionMerchantActiveMonth();
        return successGet(count);
    }

    @RequestMapping(value = "saveUserActiveMonth", method = RequestMethod.POST)
    Result saveUserActiveMonth(@RequestParam(value = "memberCount") Integer memberCount,
                               @RequestParam(value = "merchantCount") Integer merchantCount){
        reportUserActiveMonthService.saveUserActiveMonth(memberCount, merchantCount);
        return  successCreated();
    }

    @RequestMapping(value = "collectionMemberActiveAreaDaily", method = RequestMethod.GET)
    public Result<List<UserActiveDTO>> collectionMemberActiveAreaDaily() {

        List<UserActiveBO> userActiveBOS = userActiveService.collectionMemberActiveAreaDaily();
        List<UserActiveDTO> userActiveDTOS = UserActiveConverter.coverDTOS(userActiveBOS);
        return successGet(userActiveDTOS);

    }

    @RequestMapping(value = "collectionMerchantActiveAreaDaily", method = RequestMethod.GET)
    public Result<List<UserActiveDTO>> collectionMerchantActiveAreaDaily() {
        List<UserActiveBO> userActiveBOS = userActiveService.collectionMerchantActiveAreaDaily();
        List<UserActiveDTO> userActiveDTOS = UserActiveConverter.coverDTOS(userActiveBOS);
        return successGet(userActiveDTOS);
    }

    @RequestMapping(value = "saveUserActiveAreaDaily", method = RequestMethod.POST)
    public Result saveUserActiveAreaDaily(@RequestBody List<UserActiveDTO> userActiveDTOS){
        reportUserActiveAreaDailyService.saveUserActiveAreaDaily(userActiveDTOS);
        return  successCreated();
    }

    @RequestMapping(value = "saveMerchantActiveAreaDaily", method = RequestMethod.POST)
    public Result saveMerchantActiveAreaDaily(@RequestBody List<UserActiveDTO> userActiveDTOS){
        reportUserActiveAreaDailyService.saveMerchantActiveAreaDaily(userActiveDTOS);
        return  successCreated();
    }


    /**
     * 查询一个天活跃用户记录列表（按时间）
     * @param beginTime,endTime
     * @return
     */
    @RequestMapping(value = "getUserActiveListDaily",method = RequestMethod.GET)
    public Result<List<UserActiveListDTO>> getUserActiveListDaily(@RequestParam("beginTime") String beginTime,
                                                                  @RequestParam("endTime") String endTime){

        List<ReportUserActiveBO> listBOS = reportUserActiveDailyService.getUserActiveListDaily(beginTime, endTime);
        List<UserActiveListDTO> listDTOS = UserActiveConverter.coverReportDTOS(listBOS);
        return successGet(listDTOS);
    }

    /**
     * 查询一个月活跃用户记录列表（按时间）
     * @param beginTime,endTime
     * @return
     */
    @RequestMapping(value = "getUserActiveListMonth",method = RequestMethod.GET)
    public Result<List<UserActiveListDTO>> getUserActiveListMonth(@RequestParam("beginTime") String beginTime,
                                                                  @RequestParam("endTime") String endTime){
        List<ReportUserActiveBO> listBOS =  reportUserActiveDailyService.getUserActiveListMonth(beginTime,endTime);
        List<UserActiveListDTO> listDTOS = UserActiveConverter.coverReportDTOS(listBOS);
        return successGet(listDTOS);
    }

    /**
     * 运营平台根据时间查询一天活跃用户（按地区）
     * @param reportDate
     * @return
     */
    @RequestMapping(value = "getReportUserActiveAreaDailyList", method = RequestMethod.GET)
    public  Result<List<ReportUserActiveAreaDTO>> getReportUserActiveAreaDailyList(@RequestParam(value = "reportDate") String reportDate) {

        List<ReportUserActiveAreaDailyBO> listBOS = reportUserActiveDailyService.getReportUserActiveAreaDailyList(reportDate);
        List<ReportUserActiveAreaDTO> list = UserActiveConverter.coverReportAreaDTOS(listBOS);
        return successGet(list);
    }


    /**
     * 运营平台根据时间查询一个月活跃用户（按地区 ）
     * @param reportDate
     * @return
     */
    @RequestMapping(value = "getReportUserActiveAreaMonthList", method = RequestMethod.GET)
    public Result<List<ReportUserActiveAreaDTO>> getReportUserActiveAreaMonthList(@RequestParam(value = "reportDate") String reportDate) {

        List<ReportUserActiveAreaMonthBO> listBOS = reportUserActiveAreaMonthService.getReportUserActiveAreaMonthList(reportDate);
        List<ReportUserActiveAreaDTO> list = UserActiveConverter.coverReportAreaMonthDTOS(listBOS);
        return successGet(list);
    }

    /**
     * 查询地区按月活跃会员用户总数
     * @return
     */
    @RequestMapping(value = "collectionMemberActiveAreaMonth", method = RequestMethod.GET)
    public Result<List<UserActiveDTO>> collectionMemberActiveAreaMonth(){
        List<UserActiveBO> listBOS = userActiveService.collectionMemberActiveAreaMonth();
        List<UserActiveDTO> userActiveDTOS = UserActiveConverter.coverDTOS(listBOS);
        return successGet(userActiveDTOS);
    }

    /**
     * 用户活跃统计报表记录新增用户总数(地区按月)
     * @param userActiveDTOS
     * @return
     */
    @RequestMapping(value = "saveUserActiveAreaMonth", method = RequestMethod.POST)
    public Result saveUserActiveAreaMonth(@RequestBody List<UserActiveDTO> userActiveDTOS){
        reportUserActiveAreaMonthService.saveUserActiveAreaMonth(userActiveDTOS);
        return successCreated();
    }

    /**
     * 查询地区按月活跃商家总数
     * @return
     */
    @RequestMapping(value = "collectionMerchantActiveAreaMonth", method = RequestMethod.GET)
    public Result<List<UserActiveDTO>> collectionMerchantActiveAreaMonth(){
        List<UserActiveBO> listBOS = userActiveService.collectionMerchantActiveAreaMonth();
        List<UserActiveDTO> userActiveDTOS = UserActiveConverter.coverDTOS(listBOS);
        return successGet(userActiveDTOS);
    }

    /**
     * 用户活跃统计报表记录新增商家总数(地区已经存在记录更新)(地区按月)
     * @param list
     * @return
     */
    @RequestMapping(value = "saveMerchantActiveAreaMonth", method = RequestMethod.POST)
    public Result saveMerchantActiveAreaMonth(@RequestBody List<UserActiveDTO> list){
        reportUserActiveAreaMonthService.saveMerchantActiveAreaMonth(list);
        return successCreated();
    }

}
