package com.lawu.eshop.pay.srv;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 支付服务启动类
 * @author meishuquan
 * @date 2017/3/21
 */
@EnableDiscoveryClient
@SpringBootApplication
public class PaySrvApplication {

    private static Logger logger = LoggerFactory.getLogger(PaySrvApplication.class);

    public static void main(String[] args) {
        logger.info("pay-srv is starting");
        SpringApplication.run(PaySrvApplication.class, args);
        logger.info("pay-srv is started");
    }
}
