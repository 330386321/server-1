package com.lawu.eshop.statistics.srv.service.impl;

import com.lawu.eshop.statistics.srv.domain.ReportUserActiveDailyDO;
import com.lawu.eshop.statistics.srv.mapper.ReportUserActiveDailyDOMapper;
import com.lawu.eshop.statistics.srv.service.ReportUserActiveDailyService;
import com.lawu.eshop.utils.DateUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * @author zhangyong
 * @date 2017/6/29.
 */
@Service
public class ReportUserActiveDailyServiceImpl implements ReportUserActiveDailyService {

    @Autowired
    private ReportUserActiveDailyDOMapper reportUserActiveDailyDOMapper;

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
}
