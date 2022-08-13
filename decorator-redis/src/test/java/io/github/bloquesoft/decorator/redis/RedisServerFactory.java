package io.github.bloquesoft.decorator.redis;

import redis.embedded.RedisServer;

import java.util.HashMap;
import java.util.Map;

public class RedisServerFactory {

    private static final Map<Integer, RedisServer> map = new HashMap<>();

    public static RedisServer getRedisServer(int port) {
        if (!map.containsKey(port)) {
            synchronized (RedisServerFactory.class)
            {
                if (!map.containsKey(port))
                {
                    RedisServer redisServer = RedisServer.builder().port(6379).setting("maxmemory 128M").build();
                    map.put(port, redisServer);
                }
            }
        }
        return map.get(port);
    }
}
