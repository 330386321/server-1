package com.lawu.eshop.agent.srv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.ImportResource;

/**
 * @author zhangyong
 * @date 2017/8/10.
 */
@ComponentScan(value = {"com.lawu"}, excludeFilters = {@ComponentScan.Filter(value = {AgentSrvApplicationTest.class}, type = FilterType.ASSIGNABLE_TYPE)})
@SpringBootApplication
@ImportResource(locations = {"classpath:spring-test.xml"})
public class AgentSrvApplicationTest {
    public static void main(String[] args) {
        SpringApplication.run(AgentSrvApplicationTest.class, args);
    }
}
