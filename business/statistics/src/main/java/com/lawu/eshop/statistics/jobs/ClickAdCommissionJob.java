package com.lawu.eshop.statistics.jobs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.lawu.eshop.statistics.service.ClickAdCommissionService;

/**
 * 
 * <p>
 * Description: 点击广告提成
 * </p>
 * @author Yangqh
 * @date 2017年4月24日 下午3:31:10
 *
 */
public class ClickAdCommissionJob implements SimpleJob {

    private static Logger logger = LoggerFactory.getLogger(ClickAdCommissionJob.class);

    @Autowired
    private ClickAdCommissionService clickAdCommissionService;
    
    @Override
    public void execute(ShardingContext shardingContext) {
        logger.debug("------{}-{} starting------", this.getClass().getSimpleName(), shardingContext.getShardingItem());

        clickAdCommissionService.executeAutoClickAdCommission();
        
        logger.debug("------{}-{} finished------", this.getClass().getSimpleName(), shardingContext.getShardingItem());
    }
}
