package com.lawu.eshop.statistics.srv.service;

/**
 * @author zhangyong
 * @date 2017/6/29.
 */
public interface ReportUserActiveDailyService {

    void saveUserActiveDaily(Integer memberCount, Integer merchantCount);


}
