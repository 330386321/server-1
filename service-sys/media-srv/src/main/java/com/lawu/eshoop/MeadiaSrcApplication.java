package com.lawu.eshoop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * media服务启动类
 */
@EnableDiscoveryClient
@SpringBootApplication
public class MeadiaSrcApplication {

    private static Logger logger = LoggerFactory.getLogger(MeadiaSrcApplication.class);

    public static void main(String[] args) {
        logger.info("member-srv is starting");
        SpringApplication.run(MeadiaSrcApplication.class, args);
        logger.info("member-srv is started");
    }
}