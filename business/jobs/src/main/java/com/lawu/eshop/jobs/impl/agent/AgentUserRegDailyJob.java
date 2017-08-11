package com.lawu.eshop.jobs.impl.agent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;

/**
 * @author zhangyong
 * @date 2017/8/11.
 */
public class AgentUserRegDailyJob implements SimpleJob{
    private static Logger logger = LoggerFactory.getLogger(AgentUserRegDailyJob.class);

    @Override
    public void execute(ShardingContext shardingContext) {
        logger.debug("------{}-{} starting------", this.getClass().getSimpleName(), shardingContext.getShardingItem());

      //  userRegStatisticsService.executeCollectionUserRegDaily();

        logger.debug("------{}-{} finished------", this.getClass().getSimpleName(), shardingContext.getShardingItem());
    }
}
