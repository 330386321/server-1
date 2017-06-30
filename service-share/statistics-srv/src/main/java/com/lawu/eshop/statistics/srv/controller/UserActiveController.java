package com.lawu.eshop.statistics.srv.controller;

import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.statistics.dto.UserActiveDTO;
import com.lawu.eshop.statistics.srv.bo.UserActiveBO;
import com.lawu.eshop.statistics.srv.converter.UserActiveConverter;
import com.lawu.eshop.statistics.srv.service.ReportUserActiveDailyService;
import com.lawu.eshop.statistics.srv.service.ReportUserActiveMonthService;
import com.lawu.eshop.statistics.srv.service.UserActiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @RequestMapping(value = "saveUserActiveDaily", method = RequestMethod.GET)
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

    @RequestMapping(value = "saveUserActiveMonth", method = RequestMethod.GET)
    Result saveUserActiveMonth(@RequestParam(value = "memberCount") Integer memberCount,
                               @RequestParam(value = "merchantCount") Integer merchantCount){
        reportUserActiveMonthService.saveUserActiveMonth(memberCount, merchantCount);
        return  successCreated();
    }

    @RequestMapping(value = "collectionMemberActiveAreaDaily", method = RequestMethod.GET)
    Result<UserActiveDTO> collectionMemberActiveAreaDaily() {

        UserActiveBO userActiveBO = userActiveService.collectionMemberActiveAreaDaily();
        UserActiveDTO userActiveDTO = UserActiveConverter.coverDTO(userActiveBO);
        return successGet(userActiveDTO);

    }

    @RequestMapping(value = "collectionMerchantActiveAreaDaily", method = RequestMethod.GET)
    Result<UserActiveDTO> collectionMerchantActiveAreaDaily() {
        UserActiveBO userActiveBO = userActiveService.collectionMerchantActiveAreaDaily();
        UserActiveDTO userActiveDTO = UserActiveConverter.coverDTO(userActiveBO);
        return successGet(userActiveDTO);
    }

}
