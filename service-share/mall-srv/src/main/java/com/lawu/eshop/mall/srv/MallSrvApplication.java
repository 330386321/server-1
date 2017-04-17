package com.lawu.eshop.mall.srv;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

/**
 * 商城服务启动类
 * @author Sunny
 * @date 2017/3/10
 */
@EnableDiscoveryClient
@SpringBootApplication
@ComponentScan({"com.lawu.eshop"})
@ImportResource(locations={"classpath:spring.xml"})
public class MallSrvApplication {

    private static Logger logger = LoggerFactory.getLogger(MallSrvApplication.class);

    public static void main(String[] args) {
        logger.info("mall-srv is starting");
        SpringApplication.run(MallSrvApplication.class, args);
        logger.info("mall-srv is started");
    }
}
