package io.github.bloquesoft.decorator.action;

import io.github.bloquesoft.decorator.object.DecoratedMethod;
import io.github.bloquesoft.decorator.resource.ResourceRegister;

import java.lang.reflect.Method;

public interface DecorateActionReader {

    AbstractDecoratedAction read(Method method, ResourceRegister resourceRegister);
}