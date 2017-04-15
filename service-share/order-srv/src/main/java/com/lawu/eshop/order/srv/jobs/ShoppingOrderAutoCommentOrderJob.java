package com.lawu.eshop.order.srv.jobs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.lawu.eshop.order.srv.service.ShoppingOrderService;

/**
 * 
 * @author Sunny
 * @date 2017年4月15日
 */
public class ShoppingOrderAutoCommentOrderJob implements SimpleJob {

    private static Logger logger = LoggerFactory.getLogger(ShoppingOrderAutoCommentOrderJob.class);

    @Autowired
    private ShoppingOrderService shoppingOrderService;
    
    @Override
    public void execute(ShardingContext shardingContext) {
        logger.debug("------{}-{} starting------", this.getClass().getSimpleName(), shardingContext.getShardingItem());

        // 设置超时评价订单为已评价，发送MQ消息保存好评记录
        shoppingOrderService.executetAutoComment();
        
        logger.debug("------{}-{} finished------", this.getClass().getSimpleName(), shardingContext.getShardingItem());
    }
}
