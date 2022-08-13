package io.github.bloquesoft.decorator.proxy.jdk;

import io.github.bloquesoft.decorator.action.AbstractDecoratedAction;
import io.github.bloquesoft.decorator.executor.ExecutePhase;
import io.github.bloquesoft.decorator.object.DecoratedObjectRegister;
import io.github.bloquesoft.decorator.object.DecoratedMethod;
import io.github.bloquesoft.decorator.object.DecoratedObject;
import io.github.bloquesoft.decorator.proxy.TargetMethodExecuteException;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

@Slf4j
public class DecorateMethodHandler implements InvocationHandler {

    private final Object targetObject;

    private final DecoratedObjectRegister decoratedObjectRegister;

    public DecorateMethodHandler(Object targetObject, DecoratedObjectRegister decoratedObjectRegister) {
        this.targetObject = targetObject;
        this.decoratedObjectRegister = decoratedObjectRegister;
    }

    @Override
    public Object invoke(Object proxyObject, Method method, Object[] args) throws TargetMethodExecuteException {

        DecoratedObject decoratedObject = decoratedObjectRegister.get(targetObject);
        if (decoratedObject != null) {
            DecoratedMethod decoratedMethod = decoratedObject.getDecoratedMethod(method);
            if (decoratedMethod != null) {
                Object result = null;
                AbstractDecoratedAction currentAction = null;
                ExecutePhase currentExecutePhase = null;
                try {
                    for (AbstractDecoratedAction action : decoratedMethod.getDecoratedActions()) {
                        currentAction = action;

                        currentExecutePhase = ExecutePhase.OnReady;
                        action.getExecutor().onReady(action, args);

                        currentExecutePhase = ExecutePhase.DoBefore;
                        action.getExecutor().doBefore(action, args);
                    }
                    currentAction = null;
                    currentExecutePhase = ExecutePhase.DoTargetMethod;

                    result = doTargetMethod(targetObject, method, args);

                    currentExecutePhase = ExecutePhase.DoAfter;
                    for (AbstractDecoratedAction action : decoratedMethod.getDecoratedActions()) {
                        currentAction = action;
                        action.getExecutor().doAfter(action, args);
                    }
                } catch (TargetMethodExecuteException exception) {
                    throw exception;
                } catch (Exception exception) {
                    if (currentAction != null) {
                        currentAction.getExecutor().onError(currentAction, args, currentExecutePhase, exception);
                    }
                } finally {
                    for (AbstractDecoratedAction action : decoratedMethod.getDecoratedActions()) {
                        action.getExecutor().onFinally(action, args);
                    }
                }
                return result;
            }
        }
        return null;
    }

    private Object doTargetMethod(Object targetObject, Method interfaceMethod, Object[] args) throws TargetMethodExecuteException {
        try {
            return interfaceMethod.invoke(targetObject, args);
        } catch (IllegalAccessException e) {
            log.error("TargetObjectMethod execute IllegalAccessException error : " + targetObject.getClass().getName() + "." + interfaceMethod.getName() + "args:" + Arrays.toString(args));
            throw new TargetMethodExecuteException(e);
        } catch (InvocationTargetException e) {
            log.error("TargetObjectMethod execute InvocationTargetException error : " + targetObject.getClass().getName() + "." + interfaceMethod.getName() + "args:" + Arrays.toString(args));
            throw new TargetMethodExecuteException(e);
        }
    }
}