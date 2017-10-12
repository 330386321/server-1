package com.lawu.eshop.cache.srv;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;

/**
 * @author Leach
 * @date 2017/10/12
 */
//@Configuration
public class RedisConfig {
    /*@Bean
    JedisConnectionFactory connectionFactory() {
        return new JedisConnectionFactory();
    }

    @Bean
    ValueOperations<String, String> strOperations(RedisTemplate<String, String> redisTemplate) {
        return redisTemplate.opsForValue();
    }

    @Bean
    RedisTemplate<String, Integer> intRedisTemplate(JedisConnectionFactory connectionFactory) {
        RedisTemplate<String, Integer> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);
        return redisTemplate;
    }*/
}
