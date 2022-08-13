package io.github.bloquesoft.decorator.distributedLock.action;

import io.github.bloquesoft.decorator.action.AbstractDecoratedAction;
import io.github.bloquesoft.decorator.action.InstanceKey;
import io.github.bloquesoft.decorator.executor.Executor;
import io.github.bloquesoft.decorator.resource.AbstractResource;

import java.lang.reflect.Method;

public class DecoratedDistributedLockAction extends AbstractDecoratedAction {

    private final DistributedLockType lockTye;

    private final String globalKey;

    private final InstanceParameter instanceParameter;

    public String getLockKey(Object[] args) {
        if (this.lockTye == DistributedLockType.GlobalLock) {
            return globalKey;
        } else {
            Object keyObject = args[instanceParameter.getParameterIndex()];
            return ((InstanceKey) keyObject).getInstanceKey();
        }
    }

    public DecoratedDistributedLockAction(DistributedLockType keyType, String parameter, Method method, AbstractResource resource, Executor executor) {
        super(resource, executor);
        this.lockTye = keyType;
        if (this.lockTye == DistributedLockType.GlobalLock) {
            this.globalKey = parameter;
            this.instanceParameter = null;
        } else {
            this.globalKey = "";
            this.instanceParameter = InstanceParameter.create(method, parameter);
        }
    }
}