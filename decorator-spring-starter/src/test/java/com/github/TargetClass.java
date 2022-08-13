package com.github;

import io.github.bloquesoft.decorator.annotation.Decorate;
import io.github.bloquesoft.decorator.distributedLock.action.DistributedLockType;
import io.github.bloquesoft.decorator.distributedLock.annotation.DistributedLock;
import io.github.bloquesoft.decorator.redis.RedissonDistributedLockExecutor;
import org.springframework.stereotype.Component;

@Decorate
@Component
public class TargetClass implements TargetClassInterface {

    @DistributedLock(
            resourceName = "localRedis",
            lockType = DistributedLockType.InstanceLock,
            instanceParameterName = "entity",
            executorClass = RedissonDistributedLockExecutor.class)
    @Override
    public void doDistributedLock(LockEntity entity) {

    }
}