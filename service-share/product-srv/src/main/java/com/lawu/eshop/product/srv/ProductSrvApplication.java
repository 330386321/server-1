package com.lawu.eshop.product.srv;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;

/**
 * 商品服务启动类
 * @author yangqh
 * @date 2017/3/10
 */
@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication
@ImportResource(locations={"classpath:spring.xml"})
@ComponentScan({"com.lawu.eshop"})
public class ProductSrvApplication {

    private static Logger logger = LoggerFactory.getLogger(ProductSrvApplication.class);

    public static void main(String[] args) {
        logger.info("product-srv is starting");
        SpringApplication.run(ProductSrvApplication.class, args);
        logger.info("product-srv is started");
    }
}
