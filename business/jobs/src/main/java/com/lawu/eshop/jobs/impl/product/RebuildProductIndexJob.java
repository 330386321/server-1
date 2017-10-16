package com.lawu.eshop.jobs.impl.product;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.lawu.eshop.jobs.impl.ad.ClickAdCommissionJob;
import com.lawu.eshop.jobs.service.ProductService;

/**
 * @author meishuquan
 * @date 2017/10/16.
 */
public class RebuildProductIndexJob implements SimpleJob {

    private static Logger logger = LoggerFactory.getLogger(ClickAdCommissionJob.class);

    @Autowired
    private ProductService productService;

    @Override
    public void execute(ShardingContext shardingContext) {
        logger.debug("------{}-{} starting------", this.getClass().getSimpleName(), shardingContext.getShardingItem());

        productService.delInvalidProductIndex();
        productService.rebuildProductIndex();

        logger.debug("------{}-{} finished------", this.getClass().getSimpleName(), shardingContext.getShardingItem());
    }
}
