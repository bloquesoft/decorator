package com.github;

import io.github.bloquesoft.decorator.spring.EnableDecorator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import redis.embedded.RedisServer;

@SpringBootApplication
@EnableDecorator
public class Application extends SpringBootServletInitializer {

    public static void main(final String[] args) {

        SpringApplication.run(Application.class, args);

        RedisServer redisServer = RedisServer.builder().port(6380).setting("maxmemory 128M").build();
        if (!redisServer.isActive())
            redisServer.start();
    }
}