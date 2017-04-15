package com.lawu.eshop.order.srv.constants;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Configuration
@ComponentScan
@EnableAutoConfiguration
@PropertySource(value = "classpath:order.properties", ignoreResourceNotFound = true)
@Component
public class OrderConstant {
	
	@Value("${order.refund.request.time}")
	private  int refundRequestTime;

	public int getRefundRequestTime() {
		return refundRequestTime;
	}

	public void setRefundRequestTime(int refundRequestTime) {
		this.refundRequestTime = refundRequestTime;
	}
	
}
