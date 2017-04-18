package com.lawu.eshop.order.srv.jobs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.lawu.eshop.order.srv.service.ShoppingOrderService;

/**
 * 商家发货，买家超时收货
 * 平台自动收货
 * 定时任务
 * 
 * @author Sunny
 * @date 2017年4月17日
 */
public class ShoppingOrderAutoReceiptJob implements SimpleJob {

    private static Logger logger = LoggerFactory.getLogger(ShoppingOrderAutoReceiptJob.class);

    @Autowired
    private ShoppingOrderService shoppingOrderService;
    
    @Override
    public void execute(ShardingContext shardingContext) {
        logger.debug("------{}-{} starting------", this.getClass().getSimpleName(), shardingContext.getShardingItem());

        shoppingOrderService.executeAutoReceipt();
        
        logger.debug("------{}-{} finished------", this.getClass().getSimpleName(), shardingContext.getShardingItem());
    }
}
