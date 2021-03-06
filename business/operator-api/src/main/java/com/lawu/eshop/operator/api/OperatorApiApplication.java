package com.lawu.eshop.operator.api;

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
@ImportResource(locations={"classpath:spring.xml","classpath:shiro-context.xml"})
@ComponentScan(basePackages={"com.lawu.eshop"})
public class OperatorApiApplication {

    private static Logger logger = LoggerFactory.getLogger(OperatorApiApplication.class);

    public static void main(String[] args) {
        logger.info("operator-api is starting");
        SpringApplication.run(OperatorApiApplication.class, args);
        logger.info("operator-api is started");
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
