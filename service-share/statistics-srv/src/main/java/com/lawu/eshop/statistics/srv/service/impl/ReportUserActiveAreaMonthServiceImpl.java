package com.lawu.eshop.statistics.srv.service.impl;

import com.lawu.eshop.statistics.srv.bo.ReportUserActiveAreaDailyBO;
import com.lawu.eshop.statistics.srv.bo.ReportUserActiveAreaMonthBO;
import com.lawu.eshop.statistics.srv.converter.UserActiveConverter;
import com.lawu.eshop.statistics.srv.domain.ReportUserActiveAreaMonthDO;
import com.lawu.eshop.statistics.srv.domain.ReportUserActiveAreaMonthDOExample;
import com.lawu.eshop.statistics.srv.mapper.ReportUserActiveAreaMonthDOMapper;
import com.lawu.eshop.statistics.srv.service.ReportUserActiveAreaMonthService;
import com.lawu.eshop.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhangyong
 * @date 2017/6/30.
 */
@Service
public class ReportUserActiveAreaMonthServiceImpl implements ReportUserActiveAreaMonthService {

    @Autowired
    private ReportUserActiveAreaMonthDOMapper reportUserActiveAreaMonthMapper;
    @Override
    public List<ReportUserActiveAreaMonthBO> getReportUserActiveAreaMonthList(String reportDate) {

        ReportUserActiveAreaMonthDOExample example = new ReportUserActiveAreaMonthDOExample();
        example.createCriteria().andGmtReportEqualTo(DateUtil.formatDate(reportDate,"yyyy-MM"));
        List<ReportUserActiveAreaMonthDO> areaMonthDOS = reportUserActiveAreaMonthMapper.selectByExample(example);
        List<ReportUserActiveAreaMonthBO> reportUserActiveBOS = UserActiveConverter.coverReportAreaMonthBOS(areaMonthDOS);
        return reportUserActiveBOS;
    }
}
