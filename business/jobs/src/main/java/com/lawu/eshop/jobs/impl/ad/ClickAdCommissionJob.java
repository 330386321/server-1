package com.lawu.eshop.jobs.impl.ad;

import java.util.List;

import com.lawu.eshop.ad.dto.MemberAdRecodeCommissionDTO;
import com.lawu.eshop.jobs.service.AdSrvService;
import com.lawu.eshop.jobs.service.ClickAdCommissionService;
import com.lawu.jobsextend.AbstractWholePageJob;
import com.lawu.jobsextend.JobsExtendPageException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;

/**
 * 
 * <p>
 * Description: 点击广告提成
 * </p>
 * @author Yangqh
 * @date 2017年4月24日 下午3:31:10
 *
 */
public class ClickAdCommissionJob extends AbstractWholePageJob<MemberAdRecodeCommissionDTO> {

    private static Logger logger = LoggerFactory.getLogger(ClickAdCommissionJob.class);

    @Autowired
    private ClickAdCommissionService clickAdCommissionService;
    @Autowired
    private AdSrvService adService;
    
    @Override
    public boolean isStatusData() {
        return true;
    }

    @Override
    public boolean continueWhenSinglePageFail() {
        return true;
    }

    @Override
    public void executePage(List<MemberAdRecodeCommissionDTO> list) throws JobsExtendPageException {
        clickAdCommissionService.executeAutoClickAdCommission(list);
    }

    @Override
    public List<MemberAdRecodeCommissionDTO> queryPage(int offset, int pageSize) {
        List<MemberAdRecodeCommissionDTO> ads = adService.getNoneCommissionAds(offset,pageSize);
        return ads;
    }
}
