package com.lawu.eshop.order.srv.jobs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.lawu.eshop.order.srv.service.ShoppingRefundDetailService;

/**
 * 退款中-待商家处理
 * 退款类型-退款
 * 商家处理超时定时任务
 * 发送站内信和推送给商家
 * 如果商家还未操作自动退款给买家 
 * 
 * @author Sunny
 * @date 2017年4月17日
 */
public class ShoppingRefundAutoToBeConfirmedForRefundJob implements SimpleJob {

    private static Logger logger = LoggerFactory.getLogger(ShoppingRefundAutoToBeConfirmedForRefundJob.class);

    @Autowired
    private ShoppingRefundDetailService shoppingRefundDetailService;
    
    @Override
    public void execute(ShardingContext shardingContext) {
        logger.debug("------{}-{} starting------", this.getClass().getSimpleName(), shardingContext.getShardingItem());

        shoppingRefundDetailService.executeAutoToBeConfirmedForRefund();
        
        logger.debug("------{}-{} finished------", this.getClass().getSimpleName(), shardingContext.getShardingItem());
    }
}
