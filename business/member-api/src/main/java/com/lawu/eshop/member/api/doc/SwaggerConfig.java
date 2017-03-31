package com.lawu.eshop.member.api.doc;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.google.common.base.Predicate;
import com.google.common.base.Predicates;
import com.lawu.eshop.framework.web.doc.annotation.Audit;

import io.swagger.annotations.ApiOperation;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * api文档生成
 * @author Leach
 * @date 2017/3/14
 */
@Configuration
@EnableSwagger2
@ComponentScan(basePackages = {"com.lawu.eshop.member.api.controller"})
public class SwaggerConfig {
	
	@Value(value="${swagger.api.audit}")
	private boolean ISAUDIT;
	
    @Bean
    public Docket adminApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("Member API")
                .forCodeGeneration(true)
                .pathMapping("/")
                .select()
                .apis(RequestHandlerSelectors.withMethodAnnotation(ISAUDIT ? Audit.class : ApiOperation.class))
                .paths(paths())
                .build()
                .apiInfo(apiInfo())
                .useDefaultResponseMessages(false);
    }

    private Predicate<String> paths() {
        return Predicates.and(PathSelectors.regex("/.*"), Predicates.not(PathSelectors.regex("/error")));
    }


    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Member API")
                .description("会员客户端API")
                .version("1.0")
                .build();
    }
}
