package com.lawu.eshop.statistics.srv.service;

import com.lawu.eshop.statistics.srv.bo.ReportUserActiveAreaMonthBO;

import java.util.List;

/**
 * @author zhangyong
 * @date 2017/6/30.
 */
public interface ReportUserActiveAreaMonthService {

    List<ReportUserActiveAreaMonthBO> getReportUserActiveAreaMonthList(String reportDate);
}
