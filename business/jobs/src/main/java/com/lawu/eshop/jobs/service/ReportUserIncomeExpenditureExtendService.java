package com.lawu.eshop.jobs.service;

/**
 * 平台总销量扩展服务接口
 * 
 * @author Sunny
 * @date 2017年7月3日
 */
public interface ReportUserIncomeExpenditureExtendService {

	/**
	 * 定时任务<p>
	 * 保存用户支出和消费记录
	 * 
	 * @author Sunny
	 * @date 2017年7月3日
	 */
	void executeSave();
	
}
