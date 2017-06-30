package com.lawu.eshop.statistics.srv.service;

import com.lawu.eshop.statistics.param.UserActiveParam;
import com.lawu.eshop.statistics.srv.bo.ReportUserActiveAreaDailyBO;
import com.lawu.eshop.statistics.srv.bo.ReportUserActiveBO;

import java.util.List;

/**
 * @author zhangyong
 * @date 2017/6/29.
 */
public interface ReportUserActiveDailyService {

    void saveUserActiveDaily(Integer memberCount, Integer merchantCount);


    List<ReportUserActiveBO> getUserActiveListDaily(UserActiveParam param);

    List<ReportUserActiveBO> getUserActiveListMonth(UserActiveParam param);

    List<ReportUserActiveAreaDailyBO> getReportUserActiveAreaDailyList(String reportDate);
}
