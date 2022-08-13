package io.github.bloquesoft.decorator.proxy.jdk;

import io.github.bloquesoft.decorator.object.DecoratedObjectRegister;
import lombok.AllArgsConstructor;

import java.lang.reflect.Proxy;

@AllArgsConstructor
public class JdkProxyObjectFactory {

    private DecoratedObjectRegister register;

    public Object createProxyObject(Object targetObject)
    {
        return Proxy.newProxyInstance(targetObject.getClass().getClassLoader(),
                targetObject.getClass().getInterfaces(),
                new DecorateMethodHandler(targetObject, register));
    }
}