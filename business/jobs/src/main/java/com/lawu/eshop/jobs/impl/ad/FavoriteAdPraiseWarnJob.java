package com.lawu.eshop.jobs.impl.ad;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.lawu.eshop.jobs.service.FavoriteAdPraiseWarnService;

/**
 * @author zhangrc
 * @date 2017/7/27
 */
public class FavoriteAdPraiseWarnJob implements SimpleJob {

    private static Logger logger = LoggerFactory.getLogger(FavoriteAdPraiseWarnJob.class);

    @Autowired
	private FavoriteAdPraiseWarnService favoriteAdPraiseWarnService;
    
    @Override
    public void execute(ShardingContext shardingContext) {
        logger.debug("------{}-{} starting------", this.getClass().getSimpleName(), shardingContext.getShardingItem());

        favoriteAdPraiseWarnService.favoriteAdPraiseWarn();
        
        logger.debug("------{}-{} finished------", this.getClass().getSimpleName(), shardingContext.getShardingItem());
    }
}
