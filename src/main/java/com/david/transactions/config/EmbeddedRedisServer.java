package com.david.transactions.config;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import redis.embedded.RedisServer;

@Configuration
public class EmbeddedRedisServer {

    private RedisServer redisServer;

    
    public EmbeddedRedisServer(@Autowired RedisServerProperties redisProperties) {
    	
        redisServer = new RedisServer(redisProperties.getRedisPort());
    }
    

    @PostConstruct
    public void postConstruct() {
    	
        redisServer.start();
    }

    @PreDestroy
    public void preDestroy() {
    	
        redisServer.stop();
    }
}
