package io.github.bloquesoft.decorator.distributedLock.action;

import io.github.bloquesoft.decorator.action.AbstractDecoratedAction;
import io.github.bloquesoft.decorator.action.InstanceKey;
import io.github.bloquesoft.decorator.distributedLock.annotation.DistributedLock;
import io.github.bloquesoft.decorator.executor.ExecutorFactory;
import io.github.bloquesoft.decorator.resource.AbstractResource;
import io.github.bloquesoft.decorator.resource.ResourceRegister;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.powermock.api.mockito.PowerMockito;

class DecoratedDistributedLockActionReaderTest {

    @Test
    void readStatic() {

        ResourceRegister resourceRegister = PowerMockito.mock(ResourceRegister.class);
        AbstractResource resource = PowerMockito.mock(AbstractResource.class);
        PowerMockito.when(resourceRegister.contain("resource")).thenReturn(true);
        PowerMockito.when(resourceRegister.get("resource")).thenReturn(resource);
        DecoratedDistributedLockActionReader reader = new DecoratedDistributedLockActionReader();


        AbstractDecoratedAction action = reader.read(Class.class.getDeclaredMethods()[0], resourceRegister);
        Assertions.assertTrue(action instanceof DecoratedDistributedLockAction);
        Assertions.assertEquals(action.getExecutor(), ExecutorFactory.singleton().create(TestExecutor.class));
        Assertions.assertEquals(action.getResource(), resource);
        Assertions.assertEquals(((DecoratedDistributedLockAction) action).getLockKey(null), "staticLockKey");

    }

    @Test
    void readInstance() {

        ResourceRegister resourceRegister = PowerMockito.mock(ResourceRegister.class);
        AbstractResource resource = PowerMockito.mock(AbstractResource.class);
        PowerMockito.when(resourceRegister.contain("resource")).thenReturn(true);
        PowerMockito.when(resourceRegister.get("resource")).thenReturn(resource);
        DecoratedDistributedLockActionReader reader = new DecoratedDistributedLockActionReader();


        AbstractDecoratedAction action = reader.read(Class.class.getDeclaredMethods()[1], resourceRegister);
        Assertions.assertTrue(action instanceof DecoratedDistributedLockAction);

        Assertions.assertEquals(action.getExecutor(), ExecutorFactory.singleton().create(TestExecutor.class));
        Assertions.assertEquals(action.getResource(), resource);
        Object[] args = new Object[]{1, new Entity()};
        Assertions.assertEquals(((DecoratedDistributedLockAction) action).getLockKey(args), "objectKey");


    }

    
    
    public class Class{
        
        @DistributedLock(
                lockType = DistributedLockType.GlobalLock,
                resourceName = "resource",
                globalLockKey = "staticLockKey",
                executorClass = TestExecutor.class)
        public void method(){
            
        }

        @DistributedLock(resourceName = "resource", lockType = DistributedLockType.InstanceLock, instanceParameterName = "entity", executorClass = TestExecutor.class)
        public void method1(Integer number, Entity entity){

        }
    }

    public class Entity implements InstanceKey {

        @Override
        public String getInstanceKey() {
            return "objectKey";
        }
    }
}