package com.lawu.eshop.elastic.job.console;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.dangdang.ddframe.job.lite.console.ConsoleBootstrap;

/**
 * 任务监控启动类
 * @author Sunny
 * @date 2017年5月15日
 */
public class ElasticJobConsoleApplication {

    private static Logger logger = LoggerFactory.getLogger(ElasticJobConsoleApplication.class);

    public static void main(String[] args) {
        logger.info("elastic-job-console is starting");
        
        try {
			ConsoleBootstrap.main(args);
		} catch (Exception e) {
			e.printStackTrace();
		}
        
        logger.info("elastic-job-console is started");
    }

}