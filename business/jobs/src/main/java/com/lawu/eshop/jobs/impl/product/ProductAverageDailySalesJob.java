package com.lawu.eshop.jobs.impl.product;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.lawu.eshop.jobs.service.ProductAverageDailySalesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author meishuquan
 * @date 2017/4/25.
 */
public class ProductAverageDailySalesJob implements SimpleJob {

    private static Logger logger = LoggerFactory.getLogger(ProductAverageDailySalesJob.class);

    @Autowired
    private ProductAverageDailySalesService productAverageDailySalesService;

    @Override
    public void execute(ShardingContext shardingContext) {
        logger.debug("------{}-{} starting------", this.getClass().getSimpleName(), shardingContext.getShardingItem());

        productAverageDailySalesService.executeProductAverageDailySales();

        logger.debug("------{}-{} finished------", this.getClass().getSimpleName(), shardingContext.getShardingItem());
    }
}
