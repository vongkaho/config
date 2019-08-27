package com.jt.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import redis.clients.jedis.Jedis;

//标识为配制类    xml <bean id="jedis"  class="redis.clients.jedis.Jedis"/>
@Configuration
@PropertySource("classpath:/properties/redis.properties")
public class RedisConfig {
	
	@Value("${redis.host}")
	private String host;
	@Value("${redis.port}")
	private Integer port;
	
	@Bean
	public Jedis jedis() {
		
		return new Jedis(host, port);
	}
}
