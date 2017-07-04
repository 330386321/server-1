package com.lawu.eshop.cache.srv.controller;

import com.lawu.eshop.cache.srv.service.UserVisitService;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * @author zhangyong
 * @date 2017/7/3.
 */
@RestController
@RequestMapping(value = "userVisit/")
public class UserVisitController extends BaseController {

    @Autowired
    private UserVisitService userVisitService;

    @RequestMapping(value = "addUserVisitCount")
    public Result addUserVisitCount(@RequestParam("userNum") String userNum,
                                    @RequestParam("nowTimeStr") String nowTimeStr,
                                    @RequestParam("userId") Long userId){
        userVisitService.addUserVisitCount(userNum,nowTimeStr,userId);
        return successCreated();
    }

    @RequestMapping(value = "getVisitRecords",method = RequestMethod.GET)
    Map<String,String> getVisitRecords(@RequestParam("currentPage") Integer currentPage,
                                       @RequestParam("time") String time,
                                       @RequestParam("type") Byte type){
     Map<String,String> records = userVisitService.getVisitRecords(currentPage,time,type);
        return records;
    }

    @RequestMapping(value = "delVisitRecords",method = RequestMethod.DELETE)
    public Result delVisitRecords(@RequestParam("time") String time){
        userVisitService.delVisitRecords(time);
        return successDelete();
    }

}
