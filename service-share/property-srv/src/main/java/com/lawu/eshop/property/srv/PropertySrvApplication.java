package com.lawu.eshop.property.srv;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * 资产管理服务启动类
 * @author meishuquan
 * @date 2017/3/27
 */
@EnableDiscoveryClient
@SpringBootApplication
@ComponentScan({"com.lawu.eshop"})
public class PropertySrvApplication {

    private static Logger logger = LoggerFactory.getLogger(PropertySrvApplication.class);

    public static void main(String[] args) {
        logger.info("property-srv is starting");
        SpringApplication.run(PropertySrvApplication.class, args);
        logger.info("property-srv is started");
    }
}
