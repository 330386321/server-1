/**
 * 
 */
package com.lawu.eshop.product.srv;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.context.annotation.ImportResource;

/**
 * @author lihj
 * @date 2017年7月12日
 */
@ComponentScan(value = { "com.lawu.eshop" }, excludeFilters = {
@ComponentScan.Filter(value = { ProductSrvApplication.class }, type = FilterType.ASSIGNABLE_TYPE) })
@SpringBootApplication
@ImportResource(locations = {"classpath:spring-test.xml"})
public class ProductSrvApplicationTest {

	public static void main(String[] args) {
		SpringApplication.run(ProductSrvApplicationTest.class, args);
	}
}
