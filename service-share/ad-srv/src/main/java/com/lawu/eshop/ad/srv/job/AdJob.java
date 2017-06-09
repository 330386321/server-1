package com.lawu.eshop.ad.srv.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.lawu.eshop.ad.srv.service.AdService;

/**
 * @author zhangrc
 * @date 2017/4/14
 */
public class AdJob implements SimpleJob {

    private static Logger logger = LoggerFactory.getLogger(AdJob.class);

    @Autowired
	private AdService adService;
    
    @Override
    public void execute(ShardingContext shardingContext) {
        logger.debug("------{}-{} starting------", this.getClass().getSimpleName(), shardingContext.getShardingItem());

        adService.updateRacking();
        
        logger.debug("------{}-{} finished------", this.getClass().getSimpleName(), shardingContext.getShardingItem());
    }
}
