package com.lawu.eshop.member.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.ImportResource;

/**
 * <p> </p>
 *
 * @author yangqh
 * @date 2017/7/24 15:35
 */
@ComponentScan(value = {"com.lawu.eshop"}, excludeFilters = {@ComponentScan.Filter(value = {MemberApiApplication.class}, type = FilterType.ASSIGNABLE_TYPE)})
@SpringBootApplication
@ImportResource(locations={"classpath:spring-test.xml"})
public class MemberApiApplicationTest {
    public static void main(String[] args) {
        SpringApplication.run(MemberApiApplicationTest.class, args);
    }
}
