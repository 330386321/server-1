package com.lawu.eshop.statistics.srv.controller;

import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.statistics.dto.UserActiveDTO;
import com.lawu.eshop.statistics.dto.UserActiveListDTO;
import com.lawu.eshop.statistics.param.UserActiveParam;
import com.lawu.eshop.statistics.srv.bo.ReportUserActiveBO;
import com.lawu.eshop.statistics.srv.bo.UserActiveBO;
import com.lawu.eshop.statistics.srv.converter.UserActiveConverter;
import com.lawu.eshop.statistics.srv.service.ReportUserActiveAreaDailyService;
import com.lawu.eshop.statistics.srv.service.ReportUserActiveDailyService;
import com.lawu.eshop.statistics.srv.service.ReportUserActiveMonthService;
import com.lawu.eshop.statistics.srv.service.UserActiveService;
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

    @RequestMapping(value = "collectionMemberActiveDaily", method = RequestMethod.GET)
    Result<Integer> collectionMemberActiveDaily(){

        Integer count = userActiveService.collectionMemberActiveDaily();
        return successGet(count);
    }

    @RequestMapping(value = "collectionMerchantActiveDaily", method = RequestMethod.GET)
    Result<Integer> collectionMerchantActiveDaily(){
        Integer count = userActiveService.collectionMerchantActiveDaily();
        return successGet(count);
    }

    @RequestMapping(value = "saveUserActiveDaily", method = RequestMethod.POST)
    Result saveUserActiveDaily(@RequestParam(value = "memberCount") Integer memberCount,
                               @RequestParam(value = "merchantCount") Integer merchantCount){
        reportUserActiveDailyService.saveUserActiveDaily(memberCount, merchantCount);
        return  successCreated();
    }

    @RequestMapping(value = "collectionMemberActiveMonth", method = RequestMethod.GET)
    Result<Integer> collectionMemberActiveMonth(){

        Integer count = userActiveService.collectionMemberActiveMonth();
        return successGet(count);
    }

    @RequestMapping(value = "collectionMerchantActiveMonth", method = RequestMethod.GET)
    Result<Integer> collectionMerchantActiveMonth(){
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
    Result<List<UserActiveDTO>> collectionMemberActiveAreaDaily() {

        List<UserActiveBO> userActiveBOS = userActiveService.collectionMemberActiveAreaDaily();
        List<UserActiveDTO> userActiveDTOS = UserActiveConverter.coverDTOS(userActiveBOS);
        return successGet(userActiveDTOS);

    }

    @RequestMapping(value = "collectionMerchantActiveAreaDaily", method = RequestMethod.GET)
    Result<List<UserActiveDTO>> collectionMerchantActiveAreaDaily() {
        List<UserActiveBO> userActiveBOS = userActiveService.collectionMerchantActiveAreaDaily();
        List<UserActiveDTO> userActiveDTOS = UserActiveConverter.coverDTOS(userActiveBOS);
        return successGet(userActiveDTOS);
    }

    @RequestMapping(value = "userActive/saveUserActiveAreaDaily", method = RequestMethod.POST)
    Result saveUserActiveAreaDaily(@RequestBody List<UserActiveDTO> userActiveDTOS){
        reportUserActiveAreaDailyService.saveUserActiveAreaDaily(userActiveDTOS);
        return  successCreated();
    }

    @RequestMapping(value = "userActive/saveMerchantActiveAreaDaily", method = RequestMethod.POST)
    Result saveMerchantActiveAreaDaily(@RequestBody List<UserActiveDTO> userActiveDTOS){
        reportUserActiveAreaDailyService.saveMerchantActiveAreaDaily(userActiveDTOS);
        return  successCreated();
    }


    @RequestMapping(value = "getUserActiveListDaily",method = RequestMethod.POST)
    Result<List<UserActiveListDTO>> getUserActiveListDaily(@RequestBody UserActiveParam param){

        List<ReportUserActiveBO> listBOS =  reportUserActiveDailyService.getUserActiveListDaily(param);
        List<UserActiveListDTO> listDTOS = UserActiveConverter.coverReportDTOS(listBOS);
        return successGet(listDTOS);
    }

    @RequestMapping(value = "getUserActiveListMonth",method = RequestMethod.POST)
    Result<List<UserActiveListDTO>> getUserActiveListMonth(@RequestBody UserActiveParam param){
        List<ReportUserActiveBO> listBOS =  reportUserActiveDailyService.getUserActiveListMonth(param);
        List<UserActiveListDTO> listDTOS = UserActiveConverter.coverReportDTOS(listBOS);
        return successGet(listDTOS);
    }

}
