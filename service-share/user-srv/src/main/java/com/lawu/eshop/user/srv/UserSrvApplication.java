package com.lawu.eshop.user.srv;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * 用户服务启动类
 * @author Leach
 * @date 2017/3/10
 */
@EnableDiscoveryClient
@SpringBootApplication
@ComponentScan({"com.lawu.eshop"})
public class UserSrvApplication {

    private static Logger logger = LoggerFactory.getLogger(UserSrvApplication.class);

    public static void main(String[] args) {
        logger.info("user-srv is starting");
        SpringApplication.run(UserSrvApplication.class, args);
        logger.info("user-srv is started");
    }
}
