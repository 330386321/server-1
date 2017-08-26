package com.lawu.eshop.ad.srv.job;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.lawu.eshop.ad.srv.service.AdService;

/**
 * 修改平面、视频两个星期下架
 * @author zhangrc
 *
 */
public class AdFlatAndVideoJob implements SimpleJob{
	
	private static Logger logger = LoggerFactory.getLogger(AdFlatAndVideoJob.class);

    @Autowired
	private AdService adService;
    
    @Override
    public void execute(ShardingContext shardingContext) {
        logger.debug("------{}-{} starting------", this.getClass().getSimpleName(), shardingContext.getShardingItem());

        adService.updatFlatAndVideoToPuted();
        
        logger.debug("------{}-{} finished------", this.getClass().getSimpleName(), shardingContext.getShardingItem());
    }

}
