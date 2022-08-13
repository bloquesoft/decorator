package io.github.bloquesoft.decorator.distributedLock.annotation;

import io.github.bloquesoft.decorator.distributedLock.action.DistributedLockType;
import io.github.bloquesoft.decorator.executor.Executor;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DistributedLock {

    DistributedLockType lockType();

    String globalLockKey() default "";

    String instanceParameterName() default "";

    String resourceName();

    Class<? extends Executor> executorClass();
}