package com.lawu.eshop.statistics.srv.service.impl;

import com.lawu.eshop.statistics.srv.bo.ReportUserActiveAreaDailyBO;
import com.lawu.eshop.statistics.srv.bo.ReportUserActiveBO;
import com.lawu.eshop.statistics.srv.converter.UserActiveConverter;
import com.lawu.eshop.statistics.srv.domain.*;
import com.lawu.eshop.statistics.srv.mapper.ReportUserActiveAreaDailyDOMapper;
import com.lawu.eshop.statistics.srv.mapper.ReportUserActiveDailyDOMapper;
import com.lawu.eshop.statistics.srv.mapper.ReportUserActiveMonthDOMapper;
import com.lawu.eshop.statistics.srv.mapper.extend.ReportUserActiveDOMapperExtend;
import com.lawu.eshop.statistics.srv.mapper.extend.UserActiveDOMapperExtend;
import com.lawu.eshop.statistics.srv.service.ReportUserActiveDailyService;
import com.lawu.eshop.utils.DateUtil;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

/**
 * @author zhangyong
 * @date 2017/6/29.
 */
@Service
public class ReportUserActiveDailyServiceImpl implements ReportUserActiveDailyService {

    @Autowired
    private ReportUserActiveDailyDOMapper reportUserActiveDailyDOMapper;

    @Autowired
    private UserActiveDOMapperExtend userActiveDOMapperExtend;

    @Autowired
    private ReportUserActiveDOMapperExtend reportUserActiveDOMapperExtend;

    @Autowired
    private ReportUserActiveAreaDailyDOMapper reportUserActiveAreaDailyDOMapper;

    @Autowired
    private ReportUserActiveMonthDOMapper reportUserActiveMonthDOMapper;

    @Override
    @Transactional
    public void saveUserActiveDaily(Integer memberCount, Integer merchantCount) {

        ReportUserActiveDailyDO userActiveDailyDO = new ReportUserActiveDailyDO();
        userActiveDailyDO.setMemberCount(memberCount);
        userActiveDailyDO.setMerchantCount(merchantCount);
        userActiveDailyDO.setGmtCreate(new Date());
        userActiveDailyDO.setGmtReport(DateUtil.getDayBefore(DateUtil.getNowDate()));
        reportUserActiveDailyDOMapper.insertSelective(userActiveDailyDO);
    }

    @Override
    public List<ReportUserActiveBO> getUserActiveListDaily(String beginTime, String endTime) {
        if(StringUtils.isEmpty(beginTime) || StringUtils.isEmpty(endTime)){
            beginTime = DateUtil.getDateFormat(new Date(), "yyyy-MM")+"-01";
            endTime = DateUtil.getDateFormat(new Date(), "yyyy-MM-dd");
        }
        Date begin = DateUtil.formatDate(beginTime, "yyyy-MM-dd");
        Date end = DateUtil.formatDate(endTime, "yyyy-MM-dd");
        ReportUserActiveDailyDOExample example = new ReportUserActiveDailyDOExample();
        example.createCriteria().andGmtReportGreaterThanOrEqualTo(begin).andGmtReportLessThanOrEqualTo(end);
        example.setOrderByClause(" gmt_report asc ");
        List<ReportUserActiveDailyDO> dailyDOS = reportUserActiveDailyDOMapper.selectByExample(example);
        List<ReportUserActiveBO> reportUserActiveBOS = UserActiveConverter.coverReportBOSWithDOS(dailyDOS);
        return reportUserActiveBOS;
    }

    @Override
    public List<ReportUserActiveBO> getUserActiveListMonth(String beginTime, String endTime) {
        if(StringUtils.isEmpty(beginTime) || StringUtils.isEmpty(endTime)){
            beginTime = DateUtil.getDateFormat(new Date(), "yyyy"+"-01");
            endTime = DateUtil.getDateFormat(new Date(), "yyyy-MM");
        }
        ReportUserActiveMonthDOExample example = new ReportUserActiveMonthDOExample();
        Date begin = DateUtil.formatDate(beginTime+"-01", "yyyy-MM-dd");
        Date end = DateUtil.formatDate(endTime+"-01", "yyyy-MM-dd");
        example.createCriteria().andGmtReportBetween(begin, end);
        example.setOrderByClause(" gmt_report asc ");
        List<ReportUserActiveMonthDO> reportUserActiveMonthDOS = reportUserActiveMonthDOMapper.selectByExample(example);

       // List<ReportUserActiveDOView> userActiveDOViews = reportUserActiveDOMapperExtend.getUserActiveListMonth(param);
        List<ReportUserActiveBO> reportUserActiveBOS = UserActiveConverter.coverReportBOS(reportUserActiveMonthDOS);
        return reportUserActiveBOS;
    }

    @Override
    public List<ReportUserActiveAreaDailyBO> getReportUserActiveAreaDailyList(String reportDate) {
        ReportUserActiveAreaDailyDOExample example = new ReportUserActiveAreaDailyDOExample();
        example.createCriteria().andGmtReportEqualTo(DateUtil.formatDate(reportDate,"yyyy-MM-dd"));
       List<ReportUserActiveAreaDailyDO> areaDailyDOS = reportUserActiveAreaDailyDOMapper.selectByExample(example);
        List<ReportUserActiveAreaDailyBO> reportUserActiveBOS = UserActiveConverter.coverReportAreaBOS(areaDailyDOS);
        return reportUserActiveBOS;
    }
}
