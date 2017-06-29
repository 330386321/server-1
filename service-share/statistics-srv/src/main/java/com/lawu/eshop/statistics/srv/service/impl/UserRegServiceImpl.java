package com.lawu.eshop.statistics.srv.service.impl;

import com.lawu.eshop.statistics.param.UserRegAreaParam;
import com.lawu.eshop.statistics.srv.domain.ReportUserRegAreaDO;
import com.lawu.eshop.statistics.srv.domain.ReportUserRegAreaDOExample;
import com.lawu.eshop.statistics.srv.domain.ReportUserRegDailyDO;
import com.lawu.eshop.statistics.srv.domain.ReportUserRegMonthDO;
import com.lawu.eshop.statistics.srv.mapper.ReportUserRegAreaDOMapper;
import com.lawu.eshop.statistics.srv.mapper.ReportUserRegDailyDOMapper;
import com.lawu.eshop.statistics.srv.mapper.ReportUserRegMonthDOMapper;
import com.lawu.eshop.statistics.srv.service.UserRegService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * @author meishuquan
 * @date 2017/6/29.
 */
@Service
public class UserRegServiceImpl implements UserRegService {

    @Autowired
    private ReportUserRegDailyDOMapper reportUserRegDailyDOMapper;

    @Autowired
    private ReportUserRegMonthDOMapper reportUserRegMonthDOMapper;

    @Autowired
    private ReportUserRegAreaDOMapper reportUserRegAreaDOMapper;

    @Override
    public void saveUserRegDaily(Integer memberCount, Integer merchantCount) {
        ReportUserRegDailyDO reportUserRegDailyDO = new ReportUserRegDailyDO();
        reportUserRegDailyDO.setMemberCount(memberCount);
        reportUserRegDailyDO.setMerchantCount(merchantCount);
        reportUserRegDailyDO.setGmtReport(new Date());
        reportUserRegDailyDO.setGmtCreate(new Date());
        reportUserRegDailyDOMapper.insertSelective(reportUserRegDailyDO);
    }

    @Override
    public void saveUserRegMonth(Integer memberCount, Integer merchantCount) {
        ReportUserRegMonthDO reportUserRegMonthDO = new ReportUserRegMonthDO();
        reportUserRegMonthDO.setMemberCount(memberCount);
        reportUserRegMonthDO.setMerchantCount(merchantCount);
        reportUserRegMonthDO.setGmtReport(new Date());
        reportUserRegMonthDO.setGmtCreate(new Date());
        reportUserRegMonthDOMapper.insertSelective(reportUserRegMonthDO);
    }

    @Override
    public void updateUserRegArea(UserRegAreaParam param) {
        ReportUserRegAreaDO reportUserRegAreaDO = new ReportUserRegAreaDO();
        reportUserRegAreaDO.setMemberCount(param.getMemberCount());
        reportUserRegAreaDO.setMerchantCount(param.getMerchantCount());
        reportUserRegAreaDO.setMerchantCommonCount(param.getMerchantCommonCount());
        reportUserRegAreaDO.setMerchantEntityCount(param.getMerchantEntityCount());
        reportUserRegAreaDO.setGmtUpdate(new Date());
        ReportUserRegAreaDOExample example = new ReportUserRegAreaDOExample();
        example.createCriteria().andCityIdEqualTo(param.getCityId());
        reportUserRegAreaDOMapper.updateByExampleSelective(reportUserRegAreaDO, example);
    }
}
