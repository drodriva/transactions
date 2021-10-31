package com.david.transactions.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedisServerProperties {

	private int redisPort;
	

	public int getRedisPort() {
		
		return redisPort;
	}

	@Value("${transactions.redis.port}")
	public void setRedisPort(int redisPort) {
		
		this.redisPort = redisPort;
	}

}
