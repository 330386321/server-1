package com.lawu.eshop.statistics.jobs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.lawu.eshop.statistics.service.AdViewExtendService;

/**
 * 定时改变广告浏览次数
 * @author zhangrc
 *
 */
public class AdViewJob implements SimpleJob{

	private static Logger logger = LoggerFactory.getLogger(ClickAdCommissionJob.class);

    @Autowired
    private AdViewExtendService adViewExtendService;
    
    @Override
    public void execute(ShardingContext shardingContext) {
        logger.debug("------{}-{} starting------", this.getClass().getSimpleName(), shardingContext.getShardingItem());

        adViewExtendService.updateViewCount();
        
        logger.debug("------{}-{} finished------", this.getClass().getSimpleName(), shardingContext.getShardingItem());
    }

}
