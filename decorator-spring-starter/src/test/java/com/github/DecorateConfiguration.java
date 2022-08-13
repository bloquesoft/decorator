package com.github;

import io.github.bloquesoft.decorator.redis.AbstractRedisResource;
import io.github.bloquesoft.decorator.redis.SingleServerRedisResource;
import io.github.bloquesoft.decorator.resource.ResourceRegister;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DecorateConfiguration {

    @Bean
    public ResourceRegister createResourceRegister(){
        ResourceRegister resourceRegister = new ResourceRegister();
        resourceRegister.register(new SingleServerRedisResource("localRedis", "redis://localhost:6380"));
        return resourceRegister;
    }
}