package com.lawu.eshop.ad.srv.job;

import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 定时任务，设置投放和下架
 * @author zhangrc
 * @date 2017/4/11
 *
 */
@Component 
public class AdScheduleJob{
	
	private final static Logger LOG =  Logger.getLogger(AdScheduleJob.class);
	
	
	
	@Scheduled(cron = "0 0/5 * * * *")  //启动时执行一次，每五分钟记录一次
	public void jobAd(){
		
	}

}
