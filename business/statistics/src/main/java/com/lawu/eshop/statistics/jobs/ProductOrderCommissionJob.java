package com.lawu.eshop.statistics.jobs;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.lawu.eshop.statistics.service.ProductOrderCommissionService;

/**
 * 
 * <p>
 * Description: 消费提成和商家营业额提成：
 * </p>
 * <p>
 * ① 确认收货后7天定时任务将商家冻结资金加入余额时算提成； ② 商家发货后14天系统用户未确认收货时系统自动将订单金额加入商家余额时算提成
 * </p>
 * 
 * 
 * @author Yangqh
 * @date 2017年4月24日 下午3:31:10
 *
 */
public class ProductOrderCommissionJob implements SimpleJob {

	private static Logger logger = LoggerFactory.getLogger(ProductOrderCommissionJob.class);

	@Autowired
	private ProductOrderCommissionService productOrderCommissionService;

	@Override
	public void execute(ShardingContext shardingContext) {
		logger.debug("------{}-{} starting------", this.getClass().getSimpleName(), shardingContext.getShardingItem());

		productOrderCommissionService.executeAutoConsumeAndSalesCommission();

		logger.debug("------{}-{} finished------", this.getClass().getSimpleName(), shardingContext.getShardingItem());
	}
}
