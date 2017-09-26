package com.lawu.eshop.jobs.impl;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.lawu.eshop.jobs.service.IncomeMsgService;
import com.lawu.eshop.jobs.service.ReportSalesExtendService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 收益通知
 * 
 * @author yangqh
 * @date 2017年7月4日
 */
public class IncomeMsgJob implements SimpleJob {

	private static Logger logger = LoggerFactory.getLogger(IncomeMsgJob.class);

	@Autowired
	private IncomeMsgService incomeMsgService;

	@Override
	public void execute(ShardingContext shardingContext) {
		logger.debug("------{}-{} starting------", this.getClass().getSimpleName(), shardingContext.getShardingItem());

		incomeMsgService.execute();

		logger.debug("------{}-{} finished------", this.getClass().getSimpleName(), shardingContext.getShardingItem());
	}
}
