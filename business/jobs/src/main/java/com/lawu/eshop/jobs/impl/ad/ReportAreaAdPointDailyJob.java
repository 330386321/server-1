package com.lawu.eshop.jobs.impl.ad;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.lawu.eshop.jobs.service.ReportAreaAdPointDailyService;

public class ReportAreaAdPointDailyJob implements SimpleJob {
	
	private static Logger logger = LoggerFactory.getLogger(ReportEarningDailyReportJob.class);

    @Autowired
    private ReportAreaAdPointDailyService reportAreaAdPointDailyService;
    
    @Override
	public void execute(ShardingContext shardingContext) {
		logger.debug("------{hhhhhhhhhhhh}-{} starting------", this.getClass().getSimpleName(), shardingContext.getShardingItem());

		reportAreaAdPointDailyService.executeCollectReportAreaAdPointDaily();
		System.out.println("开始啦");
		logger.debug("------{hhhhhhhhhhh}-{} finished------", this.getClass().getSimpleName(), shardingContext.getShardingItem());
	}
}
