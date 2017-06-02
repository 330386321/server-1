package com.lawu.eshop.synchronization.lock.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

/**
 * Redisson配置类
 * 
 * @author Sunny
 * @date 2017年5月31日
 */
@Configuration
public class RedissonConfiguration {
	
	@Value("${redis.address}")
	private String address;
	
	@Value("${redis.password}")
	private String password;
	
	@Bean
	public RedissonClient redissonClient() {
		Config config = new Config();
		// 只设置地址跟密码，其他配置默认
		SingleServerConfig singleServerConfig = config.useSingleServer();
		singleServerConfig.setAddress(address);
		if (!StringUtils.isEmpty(password)) {
			singleServerConfig.setPassword(password);
		}
		return Redisson.create(config);
	}
}