package com.lawu.eshop.test.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * @author Leach
 * @date 2017/3/27
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients
@Configuration
@ComponentScan(basePackages={"com.lawu.eshop"})
public class TestApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(TestApiApplication.class, args);
    }

}
