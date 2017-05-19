package com.lawu.eshop.ad.srv;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

/**
 * 广告服务启动类
 * @author zhangrc
 * @date 2017/4/5
 */
@EnableDiscoveryClient
@SpringBootApplication
@ComponentScan({"com.lawu.eshop"})
@ImportResource(locations={"classpath:spring.xml"})
public class AdSrvApplication {

    private static Logger logger = LoggerFactory.getLogger(AdSrvApplication.class);

    public static void main(String[] args) {
        logger.info("ad-srv is starting");
        SpringApplication.run(AdSrvApplication.class, args);
        logger.info("ad-srv is started");
    }
}
