package com.lawu.eshop.statistics.srv.service.impl;

import com.lawu.eshop.statistics.param.UserActiveParam;
import com.lawu.eshop.statistics.srv.bo.ReportUserActiveBO;
import com.lawu.eshop.statistics.srv.converter.UserActiveConverter;
import com.lawu.eshop.statistics.srv.domain.ReportUserActiveDailyDO;
import com.lawu.eshop.statistics.srv.domain.extend.ReportUserActiveDOView;
import com.lawu.eshop.statistics.srv.mapper.ReportUserActiveDailyDOMapper;
import com.lawu.eshop.statistics.srv.mapper.extend.ReportUserActiveDOMapperExtend;
import com.lawu.eshop.statistics.srv.mapper.extend.UserActiveDOMapperExtend;
import com.lawu.eshop.statistics.srv.service.ReportUserActiveDailyService;
import com.lawu.eshop.utils.DateUtil;
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
    public List<ReportUserActiveBO> getUserActiveListDaily(UserActiveParam param) {

        List<ReportUserActiveDOView> userActiveDOViews = reportUserActiveDOMapperExtend.getUserActiveListDaily(param);
        List<ReportUserActiveBO> reportUserActiveBOS = UserActiveConverter.coverReportBOS(userActiveDOViews);
        return reportUserActiveBOS;
    }

    @Override
    public List<ReportUserActiveBO> getUserActiveListMonth(UserActiveParam param) {
        List<ReportUserActiveDOView> userActiveDOViews = reportUserActiveDOMapperExtend.getUserActiveListMonth(param);
        List<ReportUserActiveBO> reportUserActiveBOS = UserActiveConverter.coverReportBOS(userActiveDOViews);
        return reportUserActiveBOS;
    }
}
