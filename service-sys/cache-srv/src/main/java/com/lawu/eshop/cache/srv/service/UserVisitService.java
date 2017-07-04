package com.lawu.eshop.cache.srv.service;

import com.lawu.eshop.framework.core.type.UserType;

import java.util.Map;

/**
 * @author zhangyong
 * @date 2017/7/3.
 */
public interface UserVisitService {

    void addUserVisitCount(String userNum, String nowTimeStr, Long userId,UserType type);

    Map<String,String> getVisitRecords(Integer currentPage,String time,Byte type);

    void delVisitRecords(String time);
}
