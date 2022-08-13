package io.github.bloquesoft.decorator.redis;

import jodd.util.StringUtil;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.redisson.config.MasterSlaveServersConfig;
import org.redisson.config.SingleServerConfig;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;

public class RedissonFactory {

    private static final RedissonFactory REDISOON_FACTORY = new RedissonFactory();

    public static RedissonFactory singleton() {
        return REDISOON_FACTORY;
    }

    private final Map<String, RedissonClient> MAP = new HashMap<>();

    public RedissonClient getRedissonClient(AbstractRedisResource redisResource) {


        if (!MAP.containsKey(redisResource.getName())) {
            synchronized (RedissonFactory.class) {
                if (!MAP.containsKey(redisResource.getName()))
                    if (redisResource instanceof SingleServerRedisResource) {
                        RedissonClient redissonClient = createSingleServerRedissonClient((SingleServerRedisResource) redisResource);
                        MAP.put(redisResource.getName(), redissonClient);
                    } else {
                        Assert.isInstanceOf(MasterSlaveServerRedisResource.class, redisResource, "Unrecognized RedisResource " + redisResource);
                        RedissonClient redissonClient = createMasterSlaveRedissonClient((MasterSlaveServerRedisResource) redisResource);
                        MAP.put(redisResource.getName(), redissonClient);
                    }
            }
        }
        return MAP.get(redisResource.getName());
    }

    private RedissonClient createSingleServerRedissonClient(SingleServerRedisResource redisResource) {
        Config config = new Config();
        SingleServerConfig sc = config.useSingleServer()
                .setAddress(redisResource.getMasterAddress())
                .setDatabase(redisResource.getDatabase());
        if (!StringUtil.isEmpty(redisResource.getPassword())) {
            sc.setPassword(redisResource.getPassword());
        }
        return Redisson.create(config);
    }

    private RedissonClient createMasterSlaveRedissonClient(MasterSlaveServerRedisResource redisResource) {
        Config config = new Config();
        MasterSlaveServersConfig msc = config.useMasterSlaveServers()
                .setMasterAddress(redisResource.getMasterAddress())
                .addSlaveAddress(redisResource.getSlaveAddress())
                .setDatabase(redisResource.getDatabase());
        if (!StringUtil.isEmpty(redisResource.getPassword())) {
            msc.setPassword(redisResource.getPassword());
        }
        return Redisson.create(config);
    }
}