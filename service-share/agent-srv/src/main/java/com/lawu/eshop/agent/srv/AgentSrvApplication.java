package com.lawu.eshop.agent.srv;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

/**
 * @author Leach
 * @date 2017/8/7
 */
@EnableDiscoveryClient
@SpringBootApplication
@EnableFeignClients({"com.lawu"})
@ComponentScan({"com.lawu"})
public class AgentSrvApplication {

    private static Logger logger = LoggerFactory.getLogger(AgentSrvApplication.class);

    public static void main(String[] args) {
        logger.info("agent-srv is AgentSrvApplication");
        SpringApplication.run(AgentSrvApplication.class, args);
        logger.info("agent-srv is started");
    }
}
