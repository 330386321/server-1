package com.lawu.eshop.merchant.api.service;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.lawu.eshop.framework.core.type.UserType;
import com.lawu.eshop.framework.web.Result;

/**
 * @author zhangyong
 * @date 2017/7/3.
 */
@FeignClient(value = "cache-srv")
public interface UserVisitService {

    @RequestMapping(value = "userVisit/addUserVisitCount",method = RequestMethod.POST)
    Result addUserVisitCount(@RequestParam("userNum") String userNum,
                             @RequestParam("nowTimeStr") String nowTimeStr,
                             @RequestParam("userId") Long userId, @RequestParam("type") UserType type);

    /**
     * 用户上次访问接口时间
     *
     * @param userId
     * @param type
     * @return
     * @author meishuquan
     */
    @RequestMapping(value = "userVisit/addUserVisitTime", method = RequestMethod.GET)
    Result addUserVisitTime(@RequestParam("userId") Long userId, @RequestParam("type") UserType type);

    /**
     * 用户时间周期内访问接口频率
     *
     * @param userId
     * @param type
     * @param expireTime
     * @return
     * @author meishuquan
     */
    @RequestMapping(value = "userVisit/addUserVisitFrequency", method = RequestMethod.GET)
    Result addUserVisitFrequency(@RequestParam("userId") Long userId, @RequestParam("type") UserType type, @RequestParam("expireTime") Integer expireTime);

    /**
     * 查询用户上次访问接口时间
     *
     * @param userId
     * @param type
     * @return
     * @author meishuquan
     */
    @RequestMapping(value = "userVisit/getUserVisitTime", method = RequestMethod.GET)
    Result<Long> getUserVisitTime(@RequestParam("userId") Long userId, @RequestParam("type") UserType type);

    /**
     * 查询用户时间周期内访问接口频率
     *
     * @param userId
     * @param type
     * @return
     * @author meishuquan
     */
    @RequestMapping(value = "userVisit/getUserVisitFrequency", method = RequestMethod.GET)
    Result<Integer> getUserVisitFrequency(@RequestParam("userId") Long userId, @RequestParam("type") UserType type);

    /**
     * 删除用户时间周期内访问接口频率
     *
     * @param userId
     * @param type
     * @author meishuquan
     */
    @RequestMapping(value = "userVisit/delUserVisitFrequency", method = RequestMethod.DELETE)
    Result delUserVisitFrequency(@RequestParam("userId") Long userId, @RequestParam("type") UserType type);
}
