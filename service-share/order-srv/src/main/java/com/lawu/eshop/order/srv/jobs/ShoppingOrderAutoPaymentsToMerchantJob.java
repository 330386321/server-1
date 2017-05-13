package com.lawu.eshop.order.srv.jobs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.lawu.eshop.order.srv.service.ShoppingOrderService;

/**
 * 订单收货之后
 * 如果超过退款申请时间，直接付款给商家
 * 
 * @author Sunny
 * @date 2017年4月17日
 */
public class ShoppingOrderAutoPaymentsToMerchantJob implements SimpleJob {

    private static Logger logger = LoggerFactory.getLogger(ShoppingOrderAutoPaymentsToMerchantJob.class);

    @Autowired
    private ShoppingOrderService shoppingOrderService;
    
    @Override
    public void execute(ShardingContext shardingContext) {
        logger.debug("------{}-{} starting------", this.getClass().getSimpleName(), shardingContext.getShardingItem());

        shoppingOrderService.executeAutoPaymentsToMerchant();
        
        logger.debug("------{}-{} finished------", this.getClass().getSimpleName(), shardingContext.getShardingItem());
    }
}
