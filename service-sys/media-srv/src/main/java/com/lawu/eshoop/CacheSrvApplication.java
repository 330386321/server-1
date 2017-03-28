package com.lawu.eshoop;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 用户服务启动类
 * @author Leach
 * @date 2017/3/10
 */
@EnableDiscoveryClient
@SpringBootApplication
public class CacheSrvApplication {

    private static Logger logger = LoggerFactory.getLogger(CacheSrvApplication.class);

    public static void main(String[] args) {
        logger.info("cache-srv is starting");
        SpringApplication.run(CacheSrvApplication.class, args);
        logger.info("cache-srv is started");
    }
}
