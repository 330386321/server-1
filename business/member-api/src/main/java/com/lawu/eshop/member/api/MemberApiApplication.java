package com.lawu.eshop.member.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;

import com.lawu.eshop.framework.web.filter.XssFilter;

/**
 * 会员api启动类
 * @author Leach
 * @date 2017/3/10
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.lawu"})
@Configuration
@ImportResource(locations = {"classpath:spring.xml"})
@ComponentScan(basePackages = {"com.lawu"})
public class MemberApiApplication {

    private static Logger logger = LoggerFactory.getLogger(MemberApiApplication.class);

    public static void main(String[] args) {
        logger.info("member-api is starting");
        SpringApplication.run(MemberApiApplication.class, args);
        logger.info("member-api is started");
    }

    /*@Bean
    public MappingJackson2HttpMessageConverter mappingJacksonHttpMessageConverter() {
        final MappingJackson2HttpMessageConverter converter = new MappingJackson2HttpMessageConverter();
        ObjectMapper mapper = converter.getObjectMapper();
        // 为mapper注册一个带有SerializerModifier的Factory
        mapper.setSerializerFactory(mapper.getSerializerFactory().withSerializerModifier(new JsonBeanSerializerModifier()));
        return converter;
    }*/
    
    @Bean
    public FilterRegistrationBean  filterRegistrationBean() {
        FilterRegistrationBean registrationBean = new FilterRegistrationBean();
        registrationBean.setFilter(new XssFilter());
        registrationBean.addUrlPatterns("/*");
        return registrationBean;
    }
}

