package com.lawu.eshop.order.srv.jobs;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Leach
 * @date 2017/4/14
 */
public class OrderJob implements SimpleJob {

    private static Logger logger = LoggerFactory.getLogger(OrderJob.class);

    @Override
    public void execute(ShardingContext shardingContext) {
        logger.debug("------{}-{} starting------", this.getClass().getSimpleName(), shardingContext.getShardingItem());

        // 调用service处理任务
        // ...

        logger.debug("------{}-{} finished------", this.getClass().getSimpleName(), shardingContext.getShardingItem());
    }
}
