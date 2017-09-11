package com.lawu.eshop.cache.srv.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.lawu.eshop.cache.srv.service.UserVisitService;
import com.lawu.eshop.framework.core.type.UserType;
import com.lawu.eshop.framework.web.BaseController;
import com.lawu.eshop.framework.web.Result;

/**
 * @author zhangyong
 * @date 2017/7/3.
 */
@RestController
@RequestMapping(value = "userVisit/")
public class UserVisitController extends BaseController {

    @Autowired
    private UserVisitService userVisitService;

    @RequestMapping(value = "addUserVisitCount",method = RequestMethod.POST)
    public Result addUserVisitCount(@RequestParam("userNum") String userNum,
                                    @RequestParam("nowTimeStr") String nowTimeStr,
                                    @RequestParam("userId") Long userId, @RequestParam("type") UserType type){
        userVisitService.addUserVisitCount(userNum,nowTimeStr,userId,type);
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

    /**
     * 用户上次访问接口时间
     *
     * @param userId
     * @param type
     * @return
     * @author meishuquan
     */
    @RequestMapping(value = "addUserVisitTime", method = RequestMethod.GET)
    public Result addUserVisitTime(@RequestParam Long userId, @RequestParam UserType type) {
        userVisitService.addUserVisitTime(userId, type);
        return successGet();
    }

    /**
     * 用户时间周期内访问接口频率
     *
     * @param userId
     * @param type
     * @param expireTime
     * @return
     * @author meishuquan
     */
    @RequestMapping(value = "addUserVisitFrequency", method = RequestMethod.GET)
    public Result addUserVisitFrequency(@RequestParam Long userId, @RequestParam UserType type, @RequestParam Integer expireTime) {
        userVisitService.addUserVisitFrequency(userId, type, expireTime);
        return successGet();
    }

    /**
     * 查询用户上次访问接口时间
     *
     * @param userId
     * @param type
     * @return
     * @author meishuquan
     */
    @RequestMapping(value = "getUserVisitTime", method = RequestMethod.GET)
    public Result<Long> getUserVisitTime(@RequestParam Long userId, @RequestParam UserType type) {
        Long time = userVisitService.getUserVisitTime(userId, type);
        return successGet(time);
    }

    /**
     * 查询用户时间周期内访问接口频率
     *
     * @param userId
     * @param type
     * @return
     * @author meishuquan
     */
    @RequestMapping(value = "getUserVisitFrequency", method = RequestMethod.GET)
    public Result<Integer> getUserVisitFrequency(@RequestParam Long userId, @RequestParam UserType type) {
        Integer frequency = userVisitService.getUserVisitFrequency(userId, type);
        return successGet(frequency);
    }

    /**
     * 删除用户时间周期内访问接口频率
     *
     * @param userId
     * @param type
     * @return
     * @author meishuquan
     */
    @RequestMapping(value = "delUserVisitFrequency", method = RequestMethod.DELETE)
    public Result delUserVisitFrequency(@RequestParam Long userId, @RequestParam UserType type) {
        userVisitService.delUserVisitFrequency(userId, type);
        return successDelete();
    }

}
