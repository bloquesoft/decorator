package io.github.bloquesoft.decorator.testData;

import io.github.bloquesoft.decorator.annotation.Decorate;
import io.github.bloquesoft.decorator.distributedLock.action.DistributedLockType;
import io.github.bloquesoft.decorator.distributedLock.annotation.DistributedLock;
import io.github.bloquesoft.decorator.redis.RedissonDistributedLockExecutor;

@Decorate
public class ClassTest implements ClassInterfaceTest
{
    @DistributedLock(
            lockType = DistributedLockType.GlobalLock,
            globalLockKey = "key",
            resourceName = "embedded-redis",
            executorClass = RedissonDistributedLockExecutor.class)
    public void doLock(Object data) {
        System.out.println("do things in lock");
    }
}