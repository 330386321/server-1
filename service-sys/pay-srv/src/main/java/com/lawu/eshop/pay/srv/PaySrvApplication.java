package com.lawu.eshop.pay.srv;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 
 * <p>
 * Description: 支付服务启动类
 * </p>
 * @author Yangqh
 * @date 2017年4月5日 下午2:37:27
 *
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
