package com.lawu.eshop.statistics.srv.controller;

import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import com.lawu.eshop.statistics.srv.service.ReportUserActiveDailyService;
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
}
