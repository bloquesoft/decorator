package io.github.bloquesoft.decorator.distributedLock.action;

import io.github.bloquesoft.decorator.action.AbstractDecoratedAction;
import io.github.bloquesoft.decorator.action.DecorateActionReader;
import io.github.bloquesoft.decorator.distributedLock.annotation.DistributedLock;
import io.github.bloquesoft.decorator.executor.Executor;
import io.github.bloquesoft.decorator.executor.ExecutorFactory;
import io.github.bloquesoft.decorator.resource.AbstractResource;
import io.github.bloquesoft.decorator.resource.ResourceRegister;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;

@Slf4j
public class DecoratedDistributedLockActionReader implements DecorateActionReader {

    @Override
    public AbstractDecoratedAction read(Method method, ResourceRegister resourceRegister) {
        if (method.isAnnotationPresent(DistributedLock.class)) {
            DistributedLock lock = method.getAnnotation(DistributedLock.class);
            String resourceName = lock.resourceName();
            Assert.state(!StringUtils.isEmpty(resourceName), "Decorate resource " + resourceName + " could not be empty.");
            Assert.isTrue(resourceRegister.contain(resourceName), "Decorate resource " + resourceName + " could not be found.");
            AbstractResource resource = resourceRegister.get(resourceName);
            Executor executor = ExecutorFactory.singleton().create(lock.executorClass());
            if (lock.lockType() == DistributedLockType.InstanceLock) {
                String lockObjectParameterName = lock.instanceParameterName();
                Assert.state(!StringUtils.isEmpty(lockObjectParameterName), "InstanceParameterName could not be empty, method " + method.getName() + " class " + method.getDeclaringClass().getName());
                return new DecoratedDistributedLockAction(lock.lockType(), lockObjectParameterName, method, resource, executor);
            } else {
                String globalLockKey = lock.globalLockKey();
                Assert.state(!StringUtils.isEmpty(lock.globalLockKey()), "GlobalLockKey could not be empty, method " + method.getName() + " class " + method.getDeclaringClass().getName());
                return new DecoratedDistributedLockAction(lock.lockType(), globalLockKey, method, resource, executor);
            }
        }
        return null;
    }
}
