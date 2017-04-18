package com.lawu.eshop.order.srv.jobs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.lawu.eshop.order.srv.service.ShoppingRefundDetailService;

/**
 * 退款中-退款失败
 * 商家拒绝退款
 * 平台提示买家操作是否申请平台介入
 * 否则自动撤销退款申请
 * 
 * @author Sunny
 * @date 2017年4月17日
 */
public class ShoppingRefundRefundFailedJob implements SimpleJob {

    private static Logger logger = LoggerFactory.getLogger(ShoppingRefundRefundFailedJob.class);

    @Autowired
    private ShoppingRefundDetailService shoppingRefundDetailService;
    
    @Override
    public void execute(ShardingContext shardingContext) {
        logger.debug("------{}-{} starting------", this.getClass().getSimpleName(), shardingContext.getShardingItem());

        shoppingRefundDetailService.executeAutoRefundFailed();
        
        logger.debug("------{}-{} finished------", this.getClass().getSimpleName(), shardingContext.getShardingItem());
    }
}
