package com.lawu.eshop.jobs.impl.agent;

import org.springframework.beans.factory.annotation.Autowired;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.lawu.eshop.jobs.service.ReportAreaPointConsumeDailyService;

public class ReportAreaPointConsumeDailyJob implements SimpleJob {

	@Autowired
	private ReportAreaPointConsumeDailyService reportAreaPointConsumeDailyService;
	
	@Override
	public void execute(ShardingContext shardingContext) {
		reportAreaPointConsumeDailyService.executeCollectReportAreaPointConsumeDaily();
	}

}
