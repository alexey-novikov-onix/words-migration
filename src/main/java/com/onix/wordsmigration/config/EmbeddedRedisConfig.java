package com.onix.wordsmigration.config;

import org.springframework.context.annotation.Configuration;
import redis.embedded.RedisServer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Configuration
public class EmbeddedRedisConfig {

    private final RedisServer redisServer;

    public EmbeddedRedisConfig() {
        this.redisServer = new RedisServer();
    }

    @PostConstruct
    public void start() {
        this.redisServer.start();
    }

    @PreDestroy
    public void stop() {
        this.redisServer.stop();
    }

}
