package io.github.bloquesoft.decorator.redis;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import redis.embedded.RedisServer;


public class RedissonFactoryTest {

    @Test
    public void getRedissonClient() {


        RedisServer redisServer = RedisServerFactory.getRedisServer(6379);
        try
        {
            if (!redisServer.isActive()){
                redisServer.start();
            }
            SingleServerRedisResource redisResource = new SingleServerRedisResource("embedded-redis", "redis://localhost:6379");
            RedissonClient redissonClient1 = RedissonFactory.singleton().getRedissonClient(redisResource);
            RedissonClient redissonClient2 = RedissonFactory.singleton().getRedissonClient(redisResource);
            Assertions.assertEquals(redissonClient1, redissonClient2);
            RLock lock = redissonClient1.getLock("aa");
            lock.lock();
            lock.unlock();
        }
       finally {
            redisServer.stop();
        }
    }
}