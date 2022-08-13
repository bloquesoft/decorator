package io.github.bloquesoft.decorator.redis;

import io.github.bloquesoft.decorator.distributedLock.action.DecoratedDistributedLockActionReader;
import io.github.bloquesoft.decorator.distributedLock.annotation.DistributedLock;
import io.github.bloquesoft.decorator.action.DecorateActionDeclare;
import io.github.bloquesoft.decorator.action.DecorateActionDeclareRegister;
import io.github.bloquesoft.decorator.object.DecoratedObject;
import io.github.bloquesoft.decorator.object.DecoratedObjectReader;
import io.github.bloquesoft.decorator.object.DecoratedObjectRegister;
import io.github.bloquesoft.decorator.proxy.jdk.JdkProxyObjectFactory;
import io.github.bloquesoft.decorator.resource.ResourceRegister;
import io.github.bloquesoft.decorator.testData.ClassInterfaceTest;
import io.github.bloquesoft.decorator.testData.ClassTest;
import org.junit.jupiter.api.Test;
import redis.embedded.RedisServer;

class RedissonDistributedLockExecutorTest {

    @Test
    void execute() {

        RedisServer redisServer = RedisServerFactory.getRedisServer(6379);
        try {
            if (!redisServer.isActive()) {
                redisServer.start();
            }

            DecorateActionDeclare actionDeclare = new DecorateActionDeclare(DistributedLock.class, new DecoratedDistributedLockActionReader());

            DecorateActionDeclareRegister declareRegister = new DecorateActionDeclareRegister();
            declareRegister.register(actionDeclare);

            SingleServerRedisResource redisResource = new SingleServerRedisResource("embedded-redis", "redis://localhost:6379", 0, "", "");
            ResourceRegister resourceRegister = new ResourceRegister();
            resourceRegister.register(redisResource);

            ClassTest targetObject = new ClassTest();
            DecoratedObjectReader objectReader = new DecoratedObjectReader(declareRegister, resourceRegister);
            DecoratedObject decoratedObject = objectReader.read(targetObject);

            DecoratedObjectRegister objectRegister = new DecoratedObjectRegister();
            objectRegister.register(decoratedObject);

            JdkProxyObjectFactory objectFactory = new JdkProxyObjectFactory(objectRegister);
            Object proxyObject = objectFactory.createProxyObject(targetObject);
            ((ClassInterfaceTest) proxyObject).doLock(null);
        } finally {
            redisServer.stop();
        }
    }
}