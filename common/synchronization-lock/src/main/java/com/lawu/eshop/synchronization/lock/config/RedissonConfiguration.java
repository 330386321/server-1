package com.lawu.eshop.synchronization.lock.config;

import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.ClusterServersConfig;
import org.redisson.config.Config;
import org.redisson.config.SingleServerConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.util.StringUtils;

/**
 * Redisson配置类
 * 
 * @author Sunny
 * @date 2017年5月31日
 */
public class RedissonConfiguration {
	
	@Value("${redis.address}")
	private String address;
	
	@Value("${redis.password}")
	private String password;
	
	@Value("${redis.pool.size}")
	private Integer connectionPoolSize;
	
	/**
	 * Comma-separated list of "host:port" pairs to bootstrap from. This represents an
	 * "initial" list of cluster nodes and is required to have at least one entry.
	 */
	@Value("#{'${redis.cluster.nodes}'.split(',')}")
	private String[] nodes;
	
	@Bean
	public RedissonClient redissonClient() {
		Config config = new Config();
		if (StringUtils.isEmpty(address)) {
			ClusterServersConfig clusterServersConfig = config.useClusterServers();
			clusterServersConfig.setScanInterval(2000); // cluster state scan interval in milliseconds
			clusterServersConfig.addNodeAddress(nodes);
			if (connectionPoolSize != null) {
				clusterServersConfig.setSlaveConnectionPoolSize(connectionPoolSize);
			}
		} else {
			// 只设置地址跟密码，其他配置默认
			SingleServerConfig singleServerConfig = config.useSingleServer();
			singleServerConfig.setAddress(address);
			if (connectionPoolSize != null) {
				singleServerConfig.setConnectionPoolSize(connectionPoolSize);
			}
			if (!StringUtils.isEmpty(password)) {
				singleServerConfig.setPassword(password);
			}
		}
		return Redisson.create(config);
	}
}