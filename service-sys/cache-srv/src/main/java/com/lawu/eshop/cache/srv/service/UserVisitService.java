package com.lawu.eshop.cache.srv.service;

import java.util.Map;

import com.lawu.eshop.framework.core.type.UserType;

/**
 * @author zhangyong
 * @date 2017/7/3.
 */
public interface UserVisitService {

    void addUserVisitCount(String userNum, String nowTimeStr, Long userId, UserType type);

    Map<String, String> getVisitRecords(Integer currentPage, String time, Byte type);

    void delVisitRecords(String time);

    /**
     * 用户上次访问接口时间
     *
     * @param userId
     * @param type
     * @author meishuquan
     */
    @Deprecated
    void addUserVisitTime(Long userId, UserType type);

    /**
     * 用户时间周期内访问接口频率
     *
     * @param userId
     * @param type
     * @param expireTime
     * @author meishuquan
     */
    void addUserVisitFrequency(Long userId, UserType type, Integer expireTime);

    /**
     * 查询用户上次访问接口时间
     *
     * @param userId
     * @param type
     * @return
     * @author meishuquan
     */
    @Deprecated
    Long getUserVisitTime(Long userId, UserType type);

    /**
     * 查询用户时间周期内访问接口频率
     *
     * @param userId
     * @param type
     * @return
     * @author meishuquan
     */
    Integer getUserVisitFrequency(Long userId, UserType type);

    /**
     * 删除用户时间周期内访问接口频率
     *
     * @param userId
     * @param type
     * @return
     * @author meishuquan
     */
    void delUserVisitFrequency(Long userId, UserType type);

    /**
     * 保存用户访问接口次数和时间
     *
     * @param userNum
     * @param nowTimeStr
     * @param userId
     * @param type
     * @param currTime
     * @return
     * @author meishuquan
     */
    Long addUserVisitCountAndTime(String userNum, String nowTimeStr, Long userId, UserType type, String currTime);
}
