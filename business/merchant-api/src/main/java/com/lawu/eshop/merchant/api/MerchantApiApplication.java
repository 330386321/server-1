package com.lawu.eshop.merchant.api;

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
 * @author Leach
 * @date 2017/3/13
 */
@SpringBootApplication
@EnableDiscoveryClient
@EnableFeignClients(basePackages = {"com.lawu.eshop"})
@Configuration
@ImportResource(locations={"classpath:spring.xml"})
@ComponentScan(basePackages={"com.lawu.eshop","com.lawu.autotest.client"})
public class MerchantApiApplication {

    private static Logger logger = LoggerFactory.getLogger(MerchantApiApplication.class);

    public static void main(String[] args) {
        logger.info("merchant-api is starting");
        SpringApplication.run(MerchantApiApplication.class, args);
        logger.info("merchant-api is started");
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
