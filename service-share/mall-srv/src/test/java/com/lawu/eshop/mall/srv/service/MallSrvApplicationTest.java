package com.lawu.eshop.mall.srv.service;

import com.lawu.eshop.mall.srv.MallSrvApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.ImportResource;

/**
 * 商城服务启动类
 * @author Sunny
 * @date 2017/3/10
 */
@ComponentScan(value = {"com.lawu.eshop"}, excludeFilters = {@ComponentScan.Filter(value = {MallSrvApplication.class}, type = FilterType.ASSIGNABLE_TYPE)})
@SpringBootApplication
@ImportResource(locations={"classpath:spring-test.xml"})
public class MallSrvApplicationTest {

    public static void main(String[] args) {
        SpringApplication.run(MallSrvApplicationTest.class, args);
    }
}
