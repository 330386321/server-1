package com.lawu.eshop.order.srv.jobs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.lawu.eshop.order.srv.service.ShoppingRefundDetailService;

/**
 * 退款中-等待商家退款
 * 平台提示商家操作
 * 否则自动退款
 * 
 * @author Sunny
 * @date 2017年4月17日
 */
public class ShoppingRefundToBeRefundJob implements SimpleJob {

    private static Logger logger = LoggerFactory.getLogger(ShoppingRefundToBeRefundJob.class);

    @Autowired
    private ShoppingRefundDetailService shoppingRefundDetailService;
    
    @Override
    public void execute(ShardingContext shardingContext) {
        logger.debug("------{}-{} starting------", this.getClass().getSimpleName(), shardingContext.getShardingItem());

        shoppingRefundDetailService.executeAutoForToBeRefund();
        
        logger.debug("------{}-{} finished------", this.getClass().getSimpleName(), shardingContext.getShardingItem());
    }
}
